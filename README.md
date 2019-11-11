# Android MVP

This library provides a simple core framework for building Android apps using the MVP (Model-View-Presenter) pattern. 

The framework provides base interfaces that define the interactions between the View, Presenter and Model/Interactor layers:
1. **BaseRequiredViewops** (View Operations Permitted to Presenter) 
2. **BaseProvidedPresenterOps** (Presenter Operations Permitted to View) 
3. **BaseRequiredPresenterOps** (Presenter Operations Permitted to Model)
4. **BaseProvidedModelOps** (Model Operations Permitted to Presenter) 

![MVP Interfaces](https://cms-assets.tutsplus.com/uploads/users/1308/posts/26206/image/MVP_interfaces.png)


# Setup 
## 1. Provide the gradle dependency
```gradle
implementation 'com.github.mg931:android-mvp:v0.0.'
```
