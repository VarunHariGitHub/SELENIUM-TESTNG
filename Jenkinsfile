pipeline {
    agent any

    tools {
        maven 'Maven-3.9.2'
        jdk 'JDK-11'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Smoke Tests') {
            steps {
                bat 'mvn test -Dsurefire.suiteXmlFiles=testng-smoke.xml'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'All smoke tests passed successfully!'
        }
        failure {
            echo 'Smoke tests failed. Check reports for details.'
        }
    }
}
