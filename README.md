# ShamsiCal Widget — Android

ویجت تقویم شمسی برای اندروید — کلون ShamsiCal iOS

## ساختار ویجت‌ها

### Home Screen Widget (2×2)
```
┌─────────────────────┐
│      پنجشنبه       │
│                     │
│          ۴          │
│                     │
│     تیر ۱۴۰۵     │
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

> **نکته:** ویجت قفل صفحه در گوشی‌های Samsung One UI 5+ و برخی لانچرهای سفارشی قابل استفاده است. روی Pixel و AOSP خالص پشتیبانی نمی‌شود. برای اضافه کردن در قفل صفحه از Good Lock و LockStar اسفاده شود.
