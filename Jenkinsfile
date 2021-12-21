#!/usr/bin/env groovy

pipeline {

    agent {
        docker {
            image 'jhipster/jhipster'
            args '-u root'
        }
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'npm install'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'npm test'
            }
        }
    }
}




// node {
//     stage('checkout') {
//         checkout scm
//     }

//     gitlabCommitStatus('build') {
//         docker.image('jhipster/jhipster:v7.3.1').inside('-u jhipster -e GRADLE_USER_HOME=.gradle') {
//             stage('check java') {
//                 sh "java -version"
//             }

//             stage('clean') {
//                 sh "chmod +x gradlew"
//                 sh "./gradlew clean --no-daemon"
//             }
//             stage('nohttp') {
//                 sh "./gradlew checkstyleNohttp --no-daemon"
//             }

//             stage('npm install') {
//                 sh "./gradlew npm_install -PnodeInstall --no-daemon"
//             }
//             stage('backend tests') {
//                 try {
//                     sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
//                 } catch(err) {
//                     throw err
//                 } finally {
//                     junit '**/build/**/TEST-*.xml'
//                 }
//             }

//             stage('frontend tests') {
//                 try {
//                     sh "./gradlew npm_run_test -PnodeInstall --no-daemon"
//                 } catch(err) {
//                     throw err
//                 } finally {
//                     junit '**/build/test-results/TESTS-*.xml'
//                 }
//             }

//             stage('packaging') {
//                 sh "./gradlew bootJar -x test -Pprod -PnodeInstall --no-daemon"
//                 archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
//             }

//             stage('deployment') {
//                 sh "./gradlew deployHeroku --no-daemon"
//             }

//             stage('quality analysis') {
//                 withSonarQubeEnv('sonar') {
//                     sh "./gradlew sonarqube --no-daemon"
//                 }
//             }
//         }

//         def dockerImage
//         stage('publish docker') {
//             // A pre-requisite to this step is to setup authentication to the docker registry
//             // https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin#authentication-methods
//             sh "./gradlew bootJar jib -Pprod -PnodeInstall --no-daemon"
//         }
//     }
// }
