pipeline {
    agent any

    stages {
        stage('Building JAR') {
            steps {
                echo 'Building JAR'
                script {
                    sh './gradlew clean'
                    sh './gradlew jar'
                }
            }
        }
        stage('Testing Dcoker') {
            steps {
                echo 'Testing Docker'
                script {
                    sh 'docker ps'
                }
            }
        }
    }
}
