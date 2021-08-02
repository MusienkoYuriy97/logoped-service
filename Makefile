THIS_FILE := $(lastword $(MAKEFILE_LIST))
.PHONY: help build up start down destroy stop restart logs logs-api ps login-timescale login-api db-shell
build:
			docker build -t 97musienko/logoped .
			docker login
			docker push 97musienko/logoped
docker-up:
			docker-compose -f D:\logoped-service\src\docker\docker-compose.yml up
docker-down:
			docker-compose -f D:\logoped-service\src\docker\docker-compose.yml down