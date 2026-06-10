pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-11'
        MAVEN_HOME = 'C:\\Maven\\apache-maven-3.9.2'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Smoke Tests') {
            steps {
                bat "${env.MAVEN_HOME}\\bin\\mvn clean test -Dsurefire.suiteXmlFiles=testng-smoke.xml"
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
