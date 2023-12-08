pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('smeloved-wiseback')
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
                    // Debug: Print the VERSION to check its format
                    echo "Version: ${env.VERSION}"
                }
            }
        }

        stage('Build the application') {
            steps {
                echo 'Building the application...'
                sh 'mvn package'
            }
        }

        stage('Validate Docker Image Tag') {
            steps {
                script {
                    // Validate that VERSION conforms to Docker's tag naming convention
                    if (!env.VERSION.matches("[a-zA-Z0-9_.-]{1,127}")) {
                        error "Invalid Docker tag: ${env.VERSION}. Tag must match [a-zA-Z0-9_.-]{1,127}"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Ensure dockerImage is accessible in later stages
                    env.DOCKER_IMAGE = "${env.IMAGE_NAME}:${env.VERSION}"
                    sh "docker build -t ${env.DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'DOCKERHUB_CREDENTIALS', url: 'https://registry.hub.docker.com') {
                        sh "docker push ${env.DOCKER_IMAGE}"
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
