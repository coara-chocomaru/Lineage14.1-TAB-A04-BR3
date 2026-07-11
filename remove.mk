REMOVE_APPS := \
    Calendar \
    Camera2 \
    DeskClock \
    Email \
    ExactCalculator \
    Calculator \
    Jelly \
    LatinIME \
    LockClock \
    WallpaperPicker \
    AudioFX \
    Contacts \
    Eleven \
    Gallery2 \
    Recorder \
    Trebuchet \
    Messaging \
    Exchange2 \
    BlockedNumberProvider

REMOVE_TELEPHONY := \
    Dialer \
    Telephony \
    TelephonyProvider \
    TeleService \
    InCallUi \
    CarrierConfig \
    SimToolkit \
    Stk \
    Mms \
    EmailProvider \
    com.android.email \
    com.android.exchange \
    com.android.providers.blockednumber

REMOVE_NFC := \
    Nfc \
    Tag

PRODUCT_PACKAGES += $(filter-out $(REMOVE_APPS) $(REMOVE_TELEPHONY) $(REMOVE_NFC), $(PRODUCT_PACKAGES))
