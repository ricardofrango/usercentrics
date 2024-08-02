# Usercentrics

## Install

1. Run the command:

```
git clone git@github.com:ricardofrango/usercentrics.git
```

2. On the project folder run the command:

```
git checkout main
```

3. Open in Android Studio.
4. Run in device.

## Project

### What was used?

This project was developed in Kotlin using Hilt, Compose and UsercentricsSDK.

* **Hilt**

    + [About](https://dagger.dev/hilt/)

* **Compose**

    + [About](https://developer.android.com/compose)

* **UsercentricsSDK**

    + [About](https://docs.usercentrics.com/cmp_in_app_sdk/latest/)


### The structure

The project has two main packages:

- **Domain**

This package contains the use cases with the rules for the services cost calculation and to handle the state of the Usercentrics SDK.

- **Presentation**

This package contains the screen that presents the calculations of the consents for each service and the total.

## Notes

Instead of showing the cost for each service, I develop a list to show the services and their individual cost. 

## TODO's 🔨🔨

1. Some services are repeated. Maybe it could be interesting to merge the repeated services and sum their individual cost.
2. Show in the list what rules were applied for each individual service

## Support

- <ricardo_frango@hotmail.com>
