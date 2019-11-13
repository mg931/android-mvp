# Android MVP

This library provides a core framework for building Android apps using the MVP (Model-View-Presenter) pattern. MVP is one of many patterns that can be used to build Android apps and there is no clear consensus on the best approach. A consistent architecture brings significant advantages to any code base, however. When classes take on clearly defined roles, code becomes easier to change, manage, maintain and revisit months or even years later. 

This framework came out of a commercial Android app that was built for the civil engineering sector. It contains a set of components that can be extended to set up an MVP module with an Activity/Fragment as the view layer. The pattern uses four interfaces to define the operations and interactions between each layer within the stack. The basic flow is view -> presenter -> interactor -> presenter -> view (as shown below). 

![MVP Interfaces](https://cms-assets.tutsplus.com/uploads/users/1308/posts/26206/image/MVP_interfaces.png)

1. **RequiredViewops** (View Operations Permitted to Presenter) 
2. **ProvidedPresenterOps** (Presenter Operations Permitted to View) 
3. **RequiredPresenterOps** (Presenter Operations Permitted to Model)
4. **ProvidedModelOps** (Model Operations Permitted to Presenter) 

## **Advantages of MVP**
* ### Testing 
MVP is particularly useful for Android when test coverage is a high priority. The interfaces in this structure are easy to mock using only JUnit (heavy test frameworks like Robolectric are useful but not necessary).
* ### Managing the Life-cycle 
Managing state and asynchronous operations can quickly evolve into a nightmare if you write all your code inside an Activity or Fragment. To make life easier, you might tell the activity not to recreate, or fix the device orientation in portrait - short term fixes that have many drawbacks. To address these issues, this framework handles the detachment and re-attachment of the presenter/model layers when the activity is destroyed/recreated during an event like screen rotation. It  injects in a new view instance into the presenter (all under the hood) which means the presenter always has access to an up-to-date reference to the view after any long-running operation. 
* ### Separation of Concerns 
Projects and code bases grow quickly and can become brittle with too much complexity and no clear structure. By enforcing  general rules about the responsibility of classes and their function within a wider architecture, it's possible to structure projects into a series of small, testable modules that are robust and easier to manage. 

## Roles and Responsibilities 

### View
The view only knows about the presenter and it's responsibilities do not extend beyond providing the presenter with a way to access UI components. It should be as passive as possible and contain short (ideally one line) methods for updating UI components. 

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

## Installation  
### 1. Provide the gradle dependency
```gradle
implementation 'com.github.mg931:android-mvp:v0.0.5'
```

## Setup (Activity as View)   

This example assumes you are setting up an MVP module for an activity called MainActivity. The naming convention for the classes in these examples will reflect this. 

#### 1. Create an interface called MainMvpOps. 

Inside, create four nested interfaces that extend from the framework. These interfaces will define how the MVP layers for this module will operate and interact with each other.  
```
public interface MainMvpOps {

    interface RequiredViewOps extends MvpOps.BaseRequiredViewOps {
        //View operations permitted to presenter 
    }

    interface ProvidedPresenterOps extends MvpOps.BaseProvidedPresenterOps {
        //Presenter operations permitted to view 
    }

    interface RequiredPresenterOps extends MvpOps.BaseRequiredPresenterOps {
        //Presenter operations permitted to model 
    }

    interface ProvidedModelOps extends MvpOps.BaseProvidedModelOps {
        //Model operations permitted to presenter 
    }
}
```

#### 2. Create the view. 

The Activity will represent the view layer. It should extend MvpActivity and implement MainMvpOps.RequiredViewOps which is the interface that the view will expose to the presenter. The setUpComponent() method is where the MVP classes are plugged into each other. Once initialised, the framework will spit out an instance of MvpOps.ProvidedPresenterOps - which is how the view will access the operations it is permitted to call on the presenter. 

The result of setUpComponent() will be stored by the framework on the first launch of the app. When the activity is being recreated after screen rotation, for example, the presenter will already exist and will be passed straight to componentInitialized() unless the device is very low on memory or 'do not keep activities' is checked in developer options (see caveats). 
```
public class MainActivity extends MvpActivity implements MainMvpOps.RequiredViewOps {
    private MainMvpOps.ProvidedPresenterOps mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected MvpOps.ProvidedPresenterOps setUpComponent() {
        MainPresenter presenter = new MainPresenter(this);
        MainInteractor model = new MainInteractor(presenter);
        presenter.setModel(model);
        return presenter;
    }

    @Override
    protected void componentInitialized(MvpOps.ProvidedPresenterOps pres) {
        mPresenter = (MainMvpOps.ProvidedPresenterOps) pres;
    }
}

```

#### 3. Create the presenter. 

The presenter acts as a middle man and communicates with the view and model. It communicates with the view using the MainMvpOps.RequiredViewOps interface (stored as a weak reference) and with the model using the MainMvpOps.ProvidedModelOps interface. Use the getView() helper method when calling the view from inside the presenter - the view may be unavailable in certain scenarios such as when the activity is being destroyed/recreated, so this method can throw a NullPointerException. 
```
public class MainPresenter extends MvpPresenter implements MainMvpOps.ProvidedPresenterOps,
        MainMvpOps.RequiredPresenterOps  {
    private WeakReference<MainMvpOps.RequiredViewOps> mView;
    private MainMvpOps.ProvidedModelOps mModel;

    public MainPresenter(MainMvpOps.RequiredViewOps view) {
        super(view);
        mView = new WeakReference<>(view);
    }
    
    @Override
    public void setView(MvpOps.RequiredViewOps view) {
        super.setView(view);
        mView = new WeakReference<>((MainMvpOps.BaseRequiredViewOps) view);
    }

    @Override
    public void setModel(MvpOps.ProvidedModelOps model) {
        super.setModel(model);
        mModel = (MainMvpOps.BaseProvidedModelOps) model;
    }

    private MainMvpOps.RequiredViewOps getView() throws NullPointerException {
        if (mView.get() == null)
            throw new NullPointerException();
        else
            return mView.get();
    }
}
```

#### 4. Create the model/interactor. 

Like the view, the model only communicates with the presenter - however, it's only concern should be data and it should know nothing about the user interface (configuring the ui is the presenter's responsibility). It communicates with the presenter using the MainMvpOps.RequiredPresenterOps interface. 

```
public class MainInteractor extends MvpInteractor implements MainMvpOps.ProvidedModelOps {
    private MainMvpOps.RequiredPresenterOps mPresenter;
    
    public MainInteractor(MainMvpOps.RequiredPresenterOps presenter) {
        super(presenter);
        mPresenter = presenter;
    }
}

```

## Setup (Fragment as View)   

Setting up an MVP module using a fragment is similar to the activity setup. It involves different base classes, however, to handle the fragment lifecycle and provide some helper functions specific to fragments. 

#### 1. Create an interface called MainFragmentOps. 

```
public interface MainFragmentOps {
    interface RequiredViewOps extends MvpFragOps.BaseRequiredViewOps {

    }

    interface ProvidedPresenterOps extends MvpFragOps.BaseProvidedPresenterOps {

    }

    interface RequiredPresenterOps extends MvpFragOps.BaseRequiredPresenterOps {

    }

    interface ProvidedModelOps extends MvpFragOps.BaseProvidedModelOps {

    }
}
```

#### 2. Create the view.

Here Mvp fragment extends Fragment. 

```
public class MainFragment extends MvpFragment implements MainFragmentOps.RequiredViewOps {
    private MainFragmentOps.ProvidedPresenterOps mPresenter;

    @Override
    protected MainFragmentOps.ProvidedPresenterOps setUpComponent() {
        MainFragmentPresenter presenter = new MainFragmentPresenter(this);
        MainFragmentInteractor model = new MainFragmentInteractor(presenter);
        presenter.setModel(model);
        return presenter;
    }

    @Override
    protected void componentInitialized(MvpFragOps.BaseProvidedPresenterOps pres) {
        mPresenter = (MainFragmentOps.ProvidedPresenterOps) pres;
    }
}
```

#### 3. Create the presenter.

```
public class MainFragmentPresenter extends MvpFragmenPresenter implements
        MainFragmentOps.ProvidedPresenterOps, MainFragmentOps.RequiredPresenterOps {
    private WeakReference<MainFragmentOps.RequiredViewOps> mView;
    private MainFragmentOps.ProvidedModelOps mModel;

    public MainFragmentPresenter(MainFragmentOps.RequiredViewOps view) {
        super(view);
        mView = new WeakReference<>(view);
    }

    private MainFragmentOps.RequiredViewOps getView() throws NullPointerException {
        if (mView.get() == null)
            throw new NullPointerException();
        else
            return mView.get();
    }

    @Override
    public void setView(MvpFragOps.BaseRequiredViewOps view) {
        super.setView(view);
        mView = new WeakReference<>((MainFragmentOps.RequiredViewOps) view);
    }

    @Override
    public void setModel(MvpFragOps.BaseProvidedModelOps model) {
        super.setModel(model);
        mModel = (MainFragmentOps.ProvidedModelOps) model;
    }
}
```

#### 4. Create the model/interactor. 

```
public class MainFragmentInteractor extends MvpFragmentInteractor implements MainFragmentOps.ProvidedModelOps {
    private MainFragmentOps.RequiredPresenterOps mPresenter;

    public MainFragmentInteractor(MainFragmentOps.RequiredPresenterOps presenter) {
        super(presenter);
        mPresenter = presenter;
    }
}
```
## Helpers 
The framework includes a few extra helpers you can call from your presenters. 

#### Activity Presenter 
```
//get the current context
Context context = mPresenter.context();

//get the current activity
Activity activity = mPresenter.activity();

//get a string resource 
String myString = mPresenter.getStringResource(R.id.my_string);

//get activity intent  
Intent intent = mPresenter.getActivityIntent(R.id.my_string);
```
#### Fragment Presenter 
```
//get the current context
Context context = mPresenter.context();

//get the current activity
Activity activity = mPresenter.activity();

//get activity intent  
Bundle bundle = mPresenter.getFragmentArgs();
```

The following life-cycle methods are also called through the stack and you can override them inside your presenters and interactor classes. The activity onDestroy() contains an additonal flag isChangingConfigurations(). 

#### Activity 
```
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
```
#### Fragment
```
  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle inState) {
        super.onActivityCreated(inState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
```

## Caveats  

#### Retaining the presenter  
1. The framework will always attempt to store the presenter and re-attach it when an activity is recreated after a screen rotation. There are certain instances where this is not guarenteed to happen, however:
- When the app is restored after a device reset. 
- When the device is exceptionally low on memory.
- When 'Do not keep activities' is checked inside a device's developer options. 

If you need to retain state in these scenarios, use the bundle passed through onSavedInstanceState and onCreate. 

#### Avoiding memory leaks 
Excercise caution if you store classes in your presenter/interactors that retain a reference to an activity context. If the presenter and interactor layers are retained when the activity is recreated, you'll end up with a memory leak. To avoid this...
- Use an application context for things like database operations, loading resource values and starting services. Avoid retaining an activity context reference for anything ui releated, like inflating layouts, showing toasts or dialogs. 
- If you do require an activity context, use it on demand and pass it in as a method argument. Do not retain it as a class property. 
- Use a tool like [leak canary](https://github.com/square/leakcanary) to monitor whether your application is leaking memory. 




