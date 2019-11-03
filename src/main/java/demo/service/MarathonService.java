package demo.service;

import demo.dao.DAOUtils;
import demo.dao.ResultsDAO;
import demo.dao.RunnerDAO;
import demo.dao.SystemDAO;
import demo.pojo.Results;
import demo.pojo.Runner;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;

@Path("/runners")
public class MarathonService {

    @GET
    @Path("/winner/{marathonID}")
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
        return Response.status(200).entity(winner).build();
    }

    @POST
    @Path("/create")
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

}