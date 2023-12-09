pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    environment {
        IMAGE_NAME = "smeloved/wiseback"
    }

    stages {
        stage('Set variables') {
            steps {
                script {
                    sh '''
                    awk -F"[<>]" '/<version>[0-9]+\\.[0-9]+\\.[0-9]+(-SNAPSHOT)?<\\/version>/{print $3; exit}' pom.xml > ./temp.txt
                    '''
                    env.VERSION = readFile('./temp.txt').trim()
                    if (env.VERSION == '') {
                        error "VERSION is not set. Make sure pom.xml contains a valid version."
                    }
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
                    withCredentials([usernamePassword(credentialsId: 'smeloved-wiseback', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin"
                        sh "docker push ${env.DOCKER_IMAGE}"
                        sh "docker logout"
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
