# GithubRepos

A demo project based on MVVM clean architecture and material design & animations

## Architecture
![architecture](https://user-images.githubusercontent.com/24237865/37341868-555a61e6-2706-11e8-828b-8fe11c911436.png)

## Description

A simple application around the Github API.

## Goals

1. Architecture, fit for large scale projects
2. Coding practices

## Specification

The application consists of 3 parts:
- The user details screen displays user avatar, name, and company
- User repos screen displays a list of user repositories alongside with a number of open issues for each repo
- Commit details for each repo

## API

Github API documentation can be found at: https://developer.github.com/v3/
- Test user details endpoint: https://api.github.com/users/octocat
- User repos: https://api.github.com/users/octocat/repos
- Commit details: In response to user repos request

## Specs & Open-source libraries
- Minimum SDK 16
- Kotlin based, [anko](https://github.com/Kotlin/anko)
- MVVM Architecture
- Architecture Components (Lifecycle, LiveData, ViewModel, Room Persistence)
- DataBinding
- Material Design & Animations
- Github Api
- [Dagger2](https://github.com/google/dagger)
- [Retrofit2 & Gson](https://github.com/square/retrofit) for constructing the REST API
- [PreferenceRoom](https://github.com/skydoves/PreferenceRoom) for efficient managing SharedPreferences
- [Glide](https://github.com/bumptech/glide) for loading images
- [LeakCanary](https://github.com/square/leakcanary) for memory leak detection
- [AndroidSVG](https://bigbadaboom.github.io/androidsvg/) for transcoding SVG
- RecyclerViewPaginator for api paging
- ripple animation, Circular revealed animation, Shared element transition
- [Mockito](https://github.com/mockito/mockito) for Junit mock test
