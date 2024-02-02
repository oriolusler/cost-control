pipeline {
    agent any

    stages {
        stage('Build JAR') {
            steps {
                echo 'Building JAR'
                script {
                    sh './gradlew clean build'
                }
            }
        }
    }
}
