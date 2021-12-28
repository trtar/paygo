#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

 
        stage('check java') {
            sh "java -version"
        }

        stage('clean') {
            sh "chmod +x gradlew"
            sh "./gradlew clean --no-daemon"
        }
        stage('nohttp') {
            sh "./gradlew checkstyleNohttp --no-daemon"
        }

        stage('npm install') {
            sh "./gradlew npm_install -PnodeInstall --no-daemon"
        }
       

       

        stage('packaging') {
            sh "./gradlew -Pprod bootJar jibDockerBuild --no-daemon"
            archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
        }
        stage('push') {
            sh "git push heroku tariku"
        }
        
   
}
