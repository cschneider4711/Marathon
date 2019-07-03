docker run --rm --name marathon-5555 -p 127.0.0.1:5555:8080 -p 127.0.0.1:8000:8000 --cpu-shares="256" --memory-reservation="512m" --memory="1g" marathon:latest
