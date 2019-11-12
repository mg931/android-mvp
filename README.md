# Android MVP

This library provides a core framework for building Android apps using the MVP (Model-View-Presenter) pattern. 

The framework contains a set of base classes and interfaces that can be extended to set up an MVP module with an Activity/Fragment as the view layer. 

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

# Installation  
## 1. Provide the gradle dependency
```gradle
implementation 'com.github.mg931:android-mvp:v0.0.5'
```

# Setup an MVP Module (Activity as View)   

This example assumes you are setting up an MVP module for an activity called MainActivity. The naming convention for the classes in these examples will reflect this. 

#### 1. Create an interface called MainMvpOps. 

Inside, create four nested interfaces that extend from the framework. These interfaces will define how the MVP layers for this module will oeprate and interact with each other.  
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

The Activity will represent the view layer. It shoudld extend MvpActivity and implement MainMvpOps.RequiredViewOps which is the interface that the view will expose to the presenter. The setUpComponent() method is where the MVP classes are plugged into each other. Once initialised, the framework will spit out an MvpOps.ProvidedPresenterOps instance - this is how the view will communicate with the presenter. 
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

#### 2. Create the presenter. 

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



