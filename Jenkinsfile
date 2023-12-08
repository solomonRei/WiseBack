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
                    // Extract version number from pom.xml and store it in a file
                    sh '''
                    awk -F"[<>]" '/<version>[0-9]+\\.[0-9]+\\.[0-9]+(-SNAPSHOT)?<\\/version>/{print $3; exit}' pom.xml > ./temp.txt
                    '''
                    // Read the version number from the file and trim any whitespace
                    env.VERSION = readFile('./temp.txt').trim()
                    // Check if VERSION is empty
                    if (env.VERSION == '') {
                        error "VERSION is not set. Make sure pom.xml contains a valid version."
                    }
                    // Print VERSION for debugging
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
                    env.DOCKER_IMAGE = "${env.IMAGE_NAME}:${env.VERSION}"
                    echo "Building Docker Image: ${env.DOCKER_IMAGE}"
                    sh "docker build -t ${env.DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo "Pushing Docker Image: ${env.DOCKER_IMAGE}"
                    docker.withRegistry('https://registry.hub.docker.com', env.DOCKERHUB_CREDENTIALS) {
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
