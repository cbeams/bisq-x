
<p align="center">
  <img src="https://bisq.network/images/bisq-logo.svg"/>
</p>

# Bisq X: Bisq Research and Experimentation

## Introduction

Welcome. This repository is a proof of concept and prototyping environment for improvements to Bisq not well-suited to the typical pull request model. It's a place to iterate quickly and come to consensus through working code before committing to implement such changes in production Bisq repositories.

This repo is private for the time being. That may change as it takes further shape. In the meantime, please ping `@cbeams` with access requests.

The sections below describe:

- our [current goals]() and why we have them;
- our [current efforts]() to realize those goals;
- the [help we need]() to implement those efforts;
- the [repo structure]() and code for each effort;
- how to [get started]() running efforts' code;
- a few [next steps]() to get more involved.

## Current goals

### 1. Empower existing users to onboard friends and family to Bisq with a new and extraordinarily simple mobile trading experience

**Thesis and background.** Many existing users would eagerly direct their friends and family toward Bisq instead of centralized exchanges but all too often don't out of a reasonable concern that the less technically-inclined among them will have a frustrating or even unsuccessful user experience.

We designed [Bisq 2](https://bisq.network/bisq-2) and its new [Bisq Easy](https://bisq.network/bisq-easy) trading protocol to address these usability concerns. Bisq Easy has been well-received by many existing users, but wider adoption by new users remains severely limited by Bisq 2 being available only as a desktop application.

For better or worse, most people today expect and prefer web and mobile applications over desktop applications. It only follows that if we do not provide one or both of these experiences, most people will never use and benefit from what Bisq has to offer.

Under Bisq 1, it was infeasible to provide a compelling mobile experience for several reasons.

The first was a technical problem. Bisq 1 required constant connectivity to keep offers alive on its network. Such connectivity was not a practical possibility on iOS and Android platforms and the fact that the network in question was Tor-based only made the problem harder.

The second was a resourcing problem. We didn't have the dev team necessary to write and maintain complete iOS, Android and desktop versions of Bisq, especially given that key dependencies like the bitcoinj library were not available on iOS.

The third was a fundamental usability problem. Bisq 1 required users to already have a small amount of bitcoin to put up as a security deposit. This made the onboarding process for those completely new to bitcoin a non-starter and thus would have made for a disappointing mobile offering.

We considered building a thin mobile client that that would communicate with a user's always-on Bisq node as a backend, but it would not have made anything better for new and less technical users as they still would have had to have bitcoin up front and operate a desktop or server node.

Under Bisq 2 and Bisq Easy, all of these problems have been eliminated by design. It is now possible to build a truly compelling mobile offering for new and experienced Bisq users alike. To get there, though, we're going to need start thinking about and doing some things differently.




Naturally, Bisq will never host operate a centralized web application. But it can provide 

**Plan.** Our goal, therefore, is to deliver a Bisq Easy mobile experience 

With regard to a web experience.

Why we focus on friends and family. Organic growth. Word of mouth, web of trust.

No matter how well designed, software systems of any real value are inherently complex. That complexity has to be dealt with somewhere. Today, that complexity is typically handled by centralized entities, e.g. corporations.

Assisted self-custody.

### 2. Facilitate implementing our ambitious roadmap by growing the number of highly-skilled developers dedicated to working on Bisq

Thesis: 
Approach: 

### 3. Debrand


## Current efforts

### 1. Bisq Mobile

### 2. Bisq REST API and SDKs

### 3. Bisq Daemon and Terminal Client

## Help Needed

## Repository Structure

Monorepo
Micromodules
    to make dependencies very clear

build files are kept as simple as possible, favoring clarity even if it means some duplication

## Get Started

1. Install [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
2. Clone this repo into `$BISQ_X_HOME`
3. Run `./gradlew build` and expect to see `BUILD SUCCESS`
4. Run `export PATH=$PATH:$BISQ_X_HOME/bin`
5. Run `bisqd`
6. Run `bisq-cli` in a second terminal window
7. Repeat steps 6 and 7, but this time run `bisqd --debug` and notice what changes
8. Play with the interactive API docs at  http://localhost:2140/swagger-ui

## Next Steps

To go deeper, visit the README of the effort(s) that most interest you.

read the design notes


. Everything should just work and does as tested IDEA 2024.1 Community Edition.

To go deeper, visit the README of the efforts 

you can jump to the README of the efforts you're most interested in.
At this point you can jump to the README of the efforts you're most interested in.
You can now dive deeper by visiting the READMEs of the efforts you're interested in.

At this point you're ready to visit the READMEs  the efforts you're interested in.