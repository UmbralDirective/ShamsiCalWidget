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

### Home Screen Widget متنی (3×1)
```
┌──────────────────────────────┐
│    پنجشنبه ۴ تیر             │
└──────────────────────────────┘
```
همان تم ویجت 2×2 — پس‌زمینه سفید در لایت مود، مشکی در دارک مود.

### Lock Screen Widget
نمایش افقی روز، عدد و ماه — از طریق `widgetCategory="keyguard|home_screen"` روی صفحه قفل نمایش داده می‌شود (Samsung One UI و برخی لانچرها).

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
4. APK در `app/build/outputs/apk/debug/` ایجاد می‌شود

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

## ریلیز

ریلیز **خودکار نیست** و باید دستی انجام شود:

1. از تب **Actions** آخرین build موفق را باز کنید
2. از بخش **Artifacts** فایل `apk-debug` را دانلود کنید
3. در گیتهاب به **Releases → Draft a new release** بروید
4. تگ نسخه بزنید (مثلاً `v1.1`)
5. changelog دستی بنویسید
6. APK دانلودشده را آپلود کنید و منتشر کنید

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
│   │   └── JalaliCalendar.kt            ← تبدیل تاریخ
│   └── widget/
│       ├── HomeWidgetProvider.kt        ← ویجت خانه 2×2
│       ├── HomeTextWidgetProvider.kt    ← ویجت خانه متنی 3×1
│       ├── LockScreenWidgetProvider.kt  ← ویجت قفل
│       ├── WidgetUpdateService.kt       ← آپدیت شبانه
│       └── BootReceiver.kt              ← بازیابی بعد از ریست
└── res/
    ├── drawable/
    │   └── widget_background.xml        ← گوشه‌های گرد (مشترک)
    ├── font/
    │   ├── vazirmatn_medium.ttf         ← باید دانلود شود
    │   ├── vazirmatn_bold.ttf           ← باید دانلود شود
    │   └── fonts.xml
    ├── layout/
    │   ├── widget_home.xml              ← layout ویجت 2×2
    │   ├── widget_home_text.xml         ← layout ویجت متنی
    │   └── widget_lock.xml              ← layout ویجت قفل
    ├── values/
    │   ├── colors.xml                   ← تم روشن
    │   ├── strings.xml
    │   └── themes.xml
    ├── values-night/
    │   └── colors.xml                   ← تم تاریک
    └── xml/
        ├── home_widget_info.xml         ← تنظیمات ویجت 2×2
        ├── home_text_widget_info.xml    ← تنظیمات ویجت متنی
        └── lock_widget_info.xml         ← تنظیمات ویجت قفل
```

---

## نصب ویجت

### ویجت‌های صفحه اصلی
1. روی صفحه اصلی انگشت را نگه دارید
2. گزینه Widgets را انتخاب کنید
3. به دنبال **ShamsiCal Widget** بگردید — سه ویجت موجود است:
   - **تقویم شمسی (خانه)** — مربع 2×2 با روز، عدد و ماه/سال
   - **تقویم شمسی (متنی)** — نوار افقی با «پنجشنبه ۴ تیر»
4. روی صفحه رها کنید

### ویجت قفل صفحه (Samsung One UI)
1. روی صفحه قفل انگشت را نگه دارید
2. گزینه Widgets را انتخاب کنید
3. **تقویم شمسی (قفل صفحه)** را اضافه کنید

> **نکته:** ویجت قفل صفحه در گوشی‌های Samsung One UI 5+ و برخی لانچرهای سفارشی قابل استفاده است. روی Pixel و AOSP خالص پشتیبانی نمی‌شود.
