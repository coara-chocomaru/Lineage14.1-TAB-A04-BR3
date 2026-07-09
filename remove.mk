PRODUCT_PACKAGES := $(filter-out nfc, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Dialer, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Telephony, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out TelephonyProvider, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out TeleService, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out InCallUi, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out CarrierConfig, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out SimToolkit, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Stk, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Mms, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Email, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out EmailProvider, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Camera2, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Messaging, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Exchange2, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out BlockedNumberProvider, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out com.android.email, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out com.android.exchange, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out com.android.camera2, $(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out com.android.providers.blockednumber, $(PRODUCT_PACKAGES))

REMOVE_APPS += \
    Calendar \
    Camera2 \
    DeskClock \
    Email \
    ExactCalculator \
    Jelly \
    LatinIME \
    LockClock \
    WallpaperPicker \
    AudioFX \
    Contacts \
    Eleven \
    Gallery2 \
    Recorder \
    Trebuchet

PRODUCT_PACKAGES := $(filter-out $(REMOVE_APPS), $(PRODUCT_PACKAGES))
