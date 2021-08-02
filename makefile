build:
			docker build -t 97musienko/logoped .
			docker login
			docker push 97musienko/logoped
docker-up:
			docker-compose up
docker-down:
			docker-compose down