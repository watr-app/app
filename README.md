![Logo](.github/res/logo.jpg)

Source code for the Watr. Android application, built during the [Metropolia University of Applied Sciences](https://www.metropolia.fi) Mobile Health Applications course.

The JavaDoc for this repository can be found at https://watr-app.github.io/app.

## Package reference

All packages are subpackages of [`com.watr.app`](app/src/main/java/com/watr/app).

### [`constants`](app/src/main/java/com/watr/app/constants)

Various constants used across the application, including drink types, activity periods and more.

### [`datastore`](app/src/main/java/com/watr/app/datastore)

Data storage backends.

#### [`datastore.room`](app/src/main/java/com/watr/app/datastore/room)

[Room](https://developer.android.com/topic/libraries/architecture/room) datastore backend.

##### [`datastore.room.hydration`](app/src/main/java/com/watr/app/datastore/room/hydration)

[Room](https://developer.android.com/topic/libraries/architecture/room) implementation for storing hydration records and data.

#### [`datastore.sharedpreferences`](app/src/main/java/com/watr/app/datastore/sharedpreferences)

[SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) datastore backend.

##### [`datastore.sharedpreferences.settings`](app/src/main/java/com/watr/app/datastore/sharedpreferences/settings)

Application preference management backend.

##### [`datastore.sharedpreferences.userprofile`](app/src/main/java/com/watr/app/datastore/sharedpreferences/userprofile)

User profile management backend.

### [`timemgmt`](app/src/main/java/com/watr/app/timemgmt)

Activity period management backend. Responsible for determining the current activity period for the user.

### [`ui`](app/src/main/java/com/watr/app/ui)

User interface implementation.

#### [`ui.activities`](app/src/main/java/com/watr/app/ui/activities)

Collection of all activity-specific code.

#### [`ui.pages`](app/src/main/java/com/watr/app/ui/pages)

[ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2) [Fragment](https://developer.android.com/guide/components/fragments) implementations for the main activity.

#### [`ui.utils`](app/src/main/java/com/watr/app/ui/utils)

Utilities used in the user interface.

#### [`ui.viewmodels`](app/src/main/java/com/watr/app/ui/viewmodels)

[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) implementations used in the user interface.

### [`utils`](app/src/main/java/com/watr/app/utils)

Collection of various miscellaneous utilities.

## License

Copyright (c) 2020 Linus Willner, Panu Eronen and Markus Hartikainen.  
Logo by Dario Taddeo.  
Licensed under the [MIT license](LICENSE).