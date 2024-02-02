pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
               echo 'hello checkout'
            }
        }

        stage('Build') {
            steps {
                 echo 'hello build'
            }
        }

        stage('Test') {
            steps {
                 sh "docker ps"
            }
        }
    }
}
