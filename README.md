​
<p align="center">
  <img src="https://bisq.network/images/bisq-logo.svg"/>
</p>

# Bisq X: Bisq Research and Experimentation

## Introduction

Welcome. This repository is a proof of concept and prototyping environment for improvements to Bisq not well-suited to the typical pull request model. It's a place to come to consensus on potentially big and high-impact changes through working code before committing to implement those changes in production Bisq repositories.

The sections below are stubs for now but will come to include detailed information on:

- our [current goals](#current-goals) and why we have them;
- our [current efforts](#current-efforts) to realize those goals;
- the [help we need](#help-wanted) to implement those efforts;
- the [repo structure](#repo-structure) and code for each effort;
- how to [get started](#get-started) working with that code;
- and [next steps](#next-steps) for getting involved.

## Current Goals

### 1. Empower existing users to onboard friends and family to Bisq with a new and extraordinarily simple mobile trading experience

### 2. Facilitate delivering on Bisq's ambitious roadmap by growing the number of highly-skilled developers dedicated to working on it

## Current Efforts

### 1. Bisq Mobile

### 2. Bisq REST API and SDKs

### 3. Bisq Daemon and Bisq CLI Terminal Client

### 4. Bisq DCA and Market Maker demo apps

### 5. Demo presentation(s) showcasing the above

## Help Wanted

## Repo Structure

## Get Started

1. Install [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
2. Clone this repo into `$BISQ_X_HOME`
3. Run `./gradlew build` and expect to see `BUILD SUCCESS`
4. Run `export PATH=$PATH:$BISQ_X_HOME/bin`
5. Run `bisqd`
6. Run `bisq-cli listoffers` in a second terminal window
7. Repeat steps 5 and 6, but this time run `bisqd --debug` and notice what changes
8. Play with the interactive API docs at  http://localhost:2140/swagger-ui

## Next Steps

- Watch the [project walkthrough video](https://www.loom.com/share/37469ec1857140fdb3f638dbea7dd22b) (24m Loom video recorded Jun 4 2024)
- Watch the [Bisq X OpenAPI spec and client libraries video](https://www.loom.com/share/dccd603dd6d140398d40fd6fe868bb63) (22m Loom video recorded Sep 30 2024)
- Watch [A vision and high-level architecture for Bisq Mobile](https://www.loom.com/share/a35dd2b3aa92428e8c7de6461f3cdffb) (1h 12m Loom video recorded Oct 14 2024)
- Read ["Bisq Easy Mobile" discussion #2665](https://github.com/bisq-network/bisq2/discussions/2665)
- Watch [A response to Bisq Easy Mobile discussion #2665](https://www.loom.com/share/f76555e470dd449593da7b9057b7a745) (28 min Loom video recorded Oct 14 2024)
