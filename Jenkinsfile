pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    environment {
        JDK_VERSION = '17'
    }

    stages {
        stage('Detect OS and Install JDK') {
            steps {
                script {
                    def osInfo = sh(script: 'lsb_release -a || cat /etc/os-release', returnStdout: true).trim()
                    echo "OS Information: ${osInfo}"

                    if (osInfo.contains("Ubuntu") || osInfo.contains("Debian")) {
                        echo "Detected Ubuntu/Debian"
                        sh "apt update"
                        sh "apt install -y openjdk-${env.JDK_VERSION}-jdk"
                    } 
                    else if (osInfo.contains("CentOS") || osInfo.contains("Red Hat")) {
                        echo "Detected CentOS/RHEL"
                        sh "yum update"
                        sh "yum install -y java-${env.JDK_VERSION}-openjdk-devel"
                    } 
                    else {
                        error "Unknown Linux distribution"
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build the application and Docker images') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                echo 'building...'
                
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}
