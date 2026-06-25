# ShamsiCal Widget — Android

ویجت تقویم شمسی برای اندروید — کلون ShamsiCal iOS

## ساختار ویجت‌ها

### Home Screen Widget (2×2)
```
┌─────────────────────┐
│      چهارشنبه       │
│                     │
│          ۶          │
│                     │
│     اسفند ۱۴۰۴     │
└─────────────────────┘
```

### Lock Screen Widget
همان ساختار — از طریق `widgetCategory="keyguard|home_screen"` روی صفحه قفل نمایش داده می‌شود (Samsung One UI و برخی لانچرها).

---

## مراحل Build

### ۱. پیش‌نیازها
- Android Studio Hedgehog (2023.1.1) یا بالاتر
- JDK 17
- Android SDK با API 34

### ۲. فونت Vazirmatn (الزامی)

از آدرس زیر فونت را دانلود کنید:
https://fonts.google.com/specimen/Vazirmatn

دو فایل TTF را در پوشه `app/src/main/res/font/` قرار دهید:
```
Vazirmatn-Medium.ttf  →  rename به  vazirmatn_medium.ttf
Vazirmatn-Bold.ttf    →  rename به  vazirmatn_bold.ttf
```

### ۳. Build با Android Studio
1. پروژه را با `File > Open` باز کنید
2. صبر کنید Gradle sync اجرا شود
3. برای APK debug: `Build > Build Bundle(s) / APK(s) > Build APK(s)`
4. APK در `app/build/outputs/apk/debug/app-debug.apk` ایجاد می‌شود

### ۴. Build با Command Line
```bash
# Linux/Mac
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

### ۵. Signed Release APK
```bash
./gradlew assembleRelease
```
(نیاز به keystore دارد — از `Build > Generate Signed Bundle/APK` در Android Studio استفاده کنید)

---

## ویژگی‌ها

| ویژگی | توضیح |
|--------|--------|
| تبدیل تاریخ | الگوریتم Pure Kotlin، بدون نیاز به کتابخانه خارجی |
| اعداد فارسی | ۰۱۲۳۴۵۶۷۸۹ Eastern Arabic |
| دارک مود | خودکار بر اساس تنظیمات سیستم |
| لایت مود | پس‌زمینه سفید، متن تیره |
| به‌روزرسانی | هر شب نیمه‌شب با AlarmManager |
| Boot safe | بعد از ریستارت دستگاه خودکار بازیابی می‌شود |

---

## ساختار فایل‌ها

```
app/src/main/
├── AndroidManifest.xml
├── java/com/shamsicalwidget/
│   ├── util/
│   │   └── JalaliCalendar.kt       ← تبدیل تاریخ
│   └── widget/
│       ├── HomeWidgetProvider.kt   ← ویجت خانه
│       ├── LockScreenWidgetProvider.kt ← ویجت قفل
│       ├── WidgetUpdateService.kt  ← آپدیت شبانه
│       └── BootReceiver.kt         ← بازیابی بعد از ریست
└── res/
    ├── drawable/
    │   └── widget_background.xml   ← گوشه‌های گرد
    ├── font/
    │   ├── vazirmatn_medium.ttf    ← باید دانلود شود
    │   ├── vazirmatn_bold.ttf      ← باید دانلود شود
    │   └── fonts.xml
    ├── layout/
    │   └── widget_home.xml         ← layout اصلی
    ├── values/
    │   ├── colors.xml              ← تم روشن
    │   ├── strings.xml
    │   └── themes.xml
    ├── values-night/
    │   └── colors.xml              ← تم تاریک
    └── xml/
        ├── home_widget_info.xml    ← تنظیمات ویجت خانه
        └── lock_widget_info.xml    ← تنظیمات ویجت قفل
```

---

## نصب ویجت

### ویجت خانه
1. روی صفحه اصلی انگشت را نگه دارید
2. گزینه Widgets را انتخاب کنید
3. به دنبال **ShamsiCal Widget** بگردید
4. روی صفحه رها کنید

### ویجت قفل صفحه (Samsung One UI)
1. روی صفحه قفل انگشت را نگه دارید
2. گزینه Widgets را انتخاب کنید
3. **ShamsiCal Widget** را اضافه کنید

> **نکته:** ویجت قفل صفحه در گوشی‌های Samsung One UI 5+ و برخی لانچرهای سفارشی قابل استفاده است. روی Pixel و AOSP خالص پشتیبانی نمی‌شود.
