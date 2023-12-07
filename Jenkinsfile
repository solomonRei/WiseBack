pipeline {
  agent any

  tools {
    maven 'maven-3.9'
  }

  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
    SONARQUBE_CREDENTIALS = credentials('sonarqube-credentials')
    IMAGE_NAME = "smeloved/wiseback"
  }

  stages {
    stage('Set variables') {
      steps {
        script {
          sh '''
          awk -F"[<>]" '/<version>[0-9]+\\.[0-9]+\\.[0-9]+-SNAPSHOT<\\/version>/{gsub("-SNAPSHOT", "", $3); print $3; exit}' pom.xml > ./temp.txt
          '''
          env.VERSION = readFile('./temp.txt').trim()
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SONARQUBE_CREDENTIALS') {
          sh 'mvn clean verify sonar:sonar'
        }
      }
    }

    stage('Build the application') {
      steps {
        echo 'Building the application...'
        sh 'mvn package'
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          dockerImage = docker.build("${IMAGE_NAME}:${VERSION}")
        }
      }
    }

    stage('Push Docker Image to Docker Hub') {
      steps {
        script {
          docker.withRegistry('https://registry.hub.docker.com', 'DOCKERHUB_CREDENTIALS') {
            dockerImage.push()
          }
        }
      }
    }
  }

  post {
    success {
      echo "The process completed successfully."
    }
    failure {
      echo "The process failed."
    }
  }
}
