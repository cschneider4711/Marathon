package demo.service;

import demo.dao.DAOUtils;
import demo.dao.ResultsDAO;
import demo.dao.RunnerDAO;
import demo.dao.SystemDAO;
import demo.pojo.Results;
import demo.pojo.Runner;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.security.Principal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/runners")
public class MarathonService {

    @GET
    @Path("") // --> /marathon/rest/runners
    public Response listRunners() throws Exception {
        Connection connection = null;
        List<Runner> runners;
        try {
            connection = DAOUtils.getConnection();
            RunnerDAO runnerDAO = new RunnerDAO(connection);
            runners = runnerDAO.getAllRunners();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return Response.status(200).entity(runners).build();
    }

    @GET
    @Path("/winner/{marathonID}") // --> /marathon/rest/runners/winner/1
    public Response checkWinner(@PathParam("marathonID") String marathonID) throws Exception {
        Connection connection = null;
        String winner = null;
        try {
            connection = DAOUtils.getConnection();
            ResultsDAO resultsDAO = new ResultsDAO(connection);
            Results results = resultsDAO.loadResults( marathonID, false );
            if (!results.getRuns().isEmpty()) {
                Runner runner = results.getRuns().get(0).getRunner();
                winner = runner.getFirstname() + " " + runner.getLastname();
            }
        } finally {
            if (connection != null) connection.close();
        }
        // Create a map to hold the JSON response
        Map<String, String> response = new HashMap<>();
        response.put("winner", winner);
        return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/create") // --> /marathon/rest/runners/create
    public Response createRunner(RunnerRegistration runnerRegistration) throws Exception {
        Connection connection = null;
        boolean rollback = true;
        try {
            connection = DAOUtils.getConnection();
            connection.setAutoCommit(false);

            // create the runner row
            RunnerDAO runnerDAO = new RunnerDAO(connection);
            runnerDAO.createRunner(runnerRegistration.getUserName(),
                    runnerRegistration.getFirstName(),
                    runnerRegistration.getLastName(),
                    runnerRegistration.getStreet(),
                    runnerRegistration.getZip(),
                    runnerRegistration.getCity(),
                    runnerRegistration.getCreditcard(),
                    runnerRegistration.getDateOfBirth());

            // create the account
            SystemDAO systemDAO = new SystemDAO(connection);
            systemDAO.createAccount(runnerRegistration.getUserName(),
                    runnerRegistration.getPassword());

            connection.commit();
            rollback = false;
        } finally {
            if (connection != null) {
                if (rollback) connection.rollback();
                connection.close();
            }
        }
        return Response.status(200).build();
    }

    @GET
    @Path("/{runnerID}/picture/{width}/{height}") // --> /marathon/rest/runners/1/picture/500/500
    @Produces("image/jpeg")
    public Response getProfilePic(@PathParam("runnerID") String runnerID, @PathParam("width") int width, @PathParam("height") int height) throws Exception {
        Connection connection = null;
        byte[] profilePic = null;
        try {
            connection = DAOUtils.getConnection();
            RunnerDAO runnerDAO = new RunnerDAO(connection);
            Runner runner = runnerDAO.loadRunner(Long.parseLong(runnerID));
            File photoFolder = new File(System.getProperty("user.home"), "marathonImages");
            File photo = new File(photoFolder, runner.getPhotoName());
            profilePic = Files.readAllBytes(photo.toPath());

            // Create an image from the byte array
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(profilePic));

            // Create new image with RGB color model (plus resizing it)
            Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage rgbImageResized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Draw the original image onto new image
            Graphics2D g2d = rgbImageResized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            // Convert the resized image back to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(rgbImageResized, "JPEG", baos);
            baos.flush();
            final byte[] imageInByte = baos.toByteArray();
            baos.close();

            // Wrap the byte array into a StreamingOutput
            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException, WebApplicationException {
                    output.write(imageInByte);
                    output.flush();
                }
            };

            return Response.ok(stream).build();

        } finally {
            if (connection != null) connection.close();
        }
    }

    /**
     * Returns the list of runners that are not registered on any discipline (for admins to register them)
     * @param request
     * @return
     * @throws Exception
     */
    @GET
    @Path("/unregistered") // --> /marathon/rest/runners/unregistered
    public Response getUnregisteredRunners(@Context HttpServletRequest request) throws Exception {
        String sessionCookie = getSessionCookie(request);
        if (!isValidSession(request, sessionCookie, true)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Connection connection = null;
        List<Runner> runners;
        try {
            connection = DAOUtils.getConnection();
            RunnerDAO runnerDAO = new RunnerDAO(connection);
            runners = runnerDAO.getRunnersNotRegisteredOnAnyDiscipline();
        } finally {
            if (connection != null) connection.close();
        }

        return Response.status(200).entity(runners).build();
    }

    private String getSessionCookie(HttpServletRequest request) {
        // Authenticate user using session cookie
        String sessionCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JSESSIONID")) {
                    sessionCookie = cookie.getValue();
                    break;
                }
            }
        }
        return sessionCookie;
    }

    private boolean isValidSession(HttpServletRequest request, String jsessionId, boolean loggedIn) {
        if (request == null) {
            return false;
        }
        HttpSession session = request.getSession(false);
        boolean ok = session != null && jsessionId.equals(session.getId());
        if (ok && loggedIn) {
            String loggedInUser = null;
            final Principal principal = request.getUserPrincipal();
            if (principal != null) {
                loggedInUser = principal.getName();
            }
            ok = loggedInUser != null;
        }
        return ok;
    }
}