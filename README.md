# Android MVP

This library provides a simple core framework for building Android apps using the MVP (Model-View-Presenter) pattern. 

The framework provides base interfaces that define the interactions between the View, Presenter and Model/Interactor layers:
1. **BaseRequiredViewops** (View Operations Permitted to Presenter) 
2. **BaseProvidedPresenterOps** (Presenter Operations Permitted to View) 
3. **BaseRequiredPresenterOps** (Presenter Operations Permitted to Model)
4. **BaseProvidedModelOps** (Model Operations Permitted to Presenter) 

![MVP Interfaces](https://cms-assets.tutsplus.com/uploads/users/1308/posts/26206/image/MVP_interfaces.png)

**Benefits of MVP include**
* Separates UI logic from business logic. This has major advantages when dealing with the Activity life-cycle. 
* Mvp interfaces are simple to mock and you can achieve high coverage for unit and integration tests. 
* Simplifies management of threads and asynchronous operations. 
* By turning Activities into passive views, it is much easier to manage and restore application state. 

## MVP Layers and Responsibilities 

### View
The view only knows about the presenter and it's responsibilities do not extend beyond providing the presenter with a way to access UI components. It should contain short (ideally one line) methods for updating a UI component. 

For example... 
```
  @Override
  public void updateMyTextViewDialog(String text) {
    myTextView.setText(text);
 }

  @Override
  public void showToast(int length, String msg) {
    Toast.makeText(this, msg, length).show();
}

  @Override
  public void showProgressDialog() {
    progressDialog.setVisibility(View.VISIBLE);
}
```
### Presenter

The Presenter is responsible for configuring the view while calling services provided by the interactor (e.g. make http request, load data from database).

In this example, the user has clicked to open the holiday calendar. 
```
private void loadCalendar() {
        try {
            getView().showCalendarLoadingProgressDialog();
            mModel.requestCalendarUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void calendarEventsLoaded(List<CalendarEvent> eventList) {
        try {
            getView().hideCalendarLoadingProgressDialog();
            getView().configureCalendar(eventList, minDate, maxDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void errorLoadingCalendar() {
        try {
            getView().hideCalendarLoadingProgressDialog();
            getView().showToast("An error occurred loading the calendar. Please retry.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
1. The presenter responds to the click event by telling the view to show a loading dialog. 
2. The presenter tells the model to load the required data. 
3. The presenter updates the UI depending on the model's response (load the calendar or show error). 

### Model (Interactor) 

The Interactor layer is responsible for managing the application's data (the model) and enforcing business rules. It provides the presenter with a set of services via the ProvidedModelOps interface, and hides specific implementation details relating to how data is structured, stored and retrieved. 



# Setup 
## 1. Provide the gradle dependency
```gradle
implementation 'com.github.mg931:android-mvp:v0.0.'
```
