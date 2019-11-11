# Android MVP

This library provides a simple core framework for building Android apps using the MVP (Model-View-Presenter) pattern. 

The framework contains a set of core classes and base interfaces that can be extended to set up a basic MVP module with an Activity/Fragment as the view layer. 

![MVP Interfaces](https://cms-assets.tutsplus.com/uploads/users/1308/posts/26206/image/MVP_interfaces.png)

1. **RequiredViewops** (View Operations Permitted to Presenter) 
2. **ProvidedPresenterOps** (Presenter Operations Permitted to View) 
3. **RequiredPresenterOps** (Presenter Operations Permitted to Model)
4. **ProvidedModelOps** (Model Operations Permitted to Presenter) 

**Benefits of MVP**
*  By seperating UI and business logic, Activities become passive views and do not bloat into untestable god classes. 
* Interfaces between the MVP layers are simple to mock and it's possible to achieve high test coverage. 
* Presenters are not bound to the Activity/Fragment lifecycle. This simplifies asynchronous operations during scenarios like screen-rotation. Refer to sample app for an example. 

## MVP Layers and Responsibilities 

### View
The view only knows about the presenter and it's responsibilities do not extend beyond providing the presenter with a way to access UI components. It should contain short (ideally one line) methods for updating UI components. 

For example... 
```
  @Override
  public void updateMyTextView(String text) {
    myTextView.setText(text);
 }

  @Override
  public void showToast(int length, String msg) {
    Toast.makeText(this, msg, length).show();
}

  @Override
  public void setProgressBarVisibility(int visibility) {
    progressDialog.setVisibility(visibility);
}
```
### Presenter

The Presenter is responsible for configuring the user interface and delegating data-related operations to the interactor (e.g. save, store, update, retrieve).

This example shows how the presenter might handle the user entering a note which is then posted to a web API. 
```
    @Override
    public void storeNewNoteClicked() {
        Rescue.execute(() -> {
            getView().setProgressBarVisibility(VISIBLE);
            String note = getView().getInputtedNote(); 
            mModel.storeNote(note); 
        }); 
    }

    @Override
    public void noteSuccesfullyStored() {
        Rescue.execute(() -> {
           getView().setProgressBarVisibility(GONE);
           showSuccessSnackBar(R.string.note_successfully_saved_msg); 
        }); 
    }

    @Override
    public void errorStoringNote(String error) {
       Rescue.execute(() -> {
            getView().setProgressBarVisibility(GONE);
            showErrorSnackBar("Something went wrong: " + error); 
        }); 
    }
```
1. The presenter responds to the click event by telling the view to show a progress bar. 
2. The presenter tells the model to store the inputted data. 
3. The presenter updates the UI depending on the model's response (success or failure). 

### Model (Interactor) 

The Interactor layer contains the application's data and is where business rules are enforced. It provides the presenter with a set of services via the ProvidedModelOps interface, and hides specific implementation details relating to how data is structured, stored and retrieved. 

# Setup 
## 1. Provide the gradle dependency
```gradle
implementation 'com.github.mg931:android-mvp:v0.0.5'
```
