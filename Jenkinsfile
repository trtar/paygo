#!/usr/bin/env groovy

node {


    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }
    stage('deployment') {
        withCredentials([
                 string(credentialsId: ‘HEROKU_API_KEY’, variable: ‘HEROKU_API_KEY’),
                 string(credentialsId: ‘HEROKU_APP_NAME’, variable: ‘HEROKU_APP_NAME’),
             ]){

        sh "./gradlew deployHeroku --no-daemon"
     }
       
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
        sh "./gradlew bootJar -x test -Pprod -PnodeInstall --no-daemon"
        archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
    }

    

}
