# Lucky Dangle — Android Studio (Kotlin)

## அம்சம்
- திரையின் மேல்பகுதி நடுவிலிருந்து கயிறு தொங்கும்.
- தங்க நிறப் பதக்கம் மெதுவாக அசையும்.
- மற்ற Apps மேல் overlay ஆகக் காட்டப்படும்.
- Start / Stop பொத்தான்கள் உள்ளன.

## முக்கியமான குறிப்பு
இந்த prototype கயிற்றை திரையின் மேல்பகுதி நடுவில் இருந்து தொடங்குகிறது. எல்லா Android மொபைல்களிலும் punch-hole camera அதே இடத்தில் இருக்காது. குறிப்பிட்ட Redmi மாடலுக்காக `DangleOverlayService.kt`-இல் உள்ள `x` மற்றும் `y` offset-ஐச் சரிசெய்ய வேண்டியிருக்கலாம்.

இந்த அம்சத்திற்கு கேமராவை உண்மையில் இயக்க வேண்டியதில்லை; எனவே CAMERA permission சேர்க்கப்படவில்லை. Punch-hole-ஐத் தொடக்கப் புள்ளியாகக் காட்டுவது மட்டும். எதிர்காலத்தில் live camera preview சேர்த்தால் மட்டுமே CAMERA permission தேவைப்படும்.

## திறப்பது
1. Android Studio நிறுவவும்.
2. `LuckyDangleAndroid` folder-ஐ Open செய்யவும்.
3. Gradle Sync முடியும் வரை காத்திருக்கவும்.
4. USB debugging இயக்கிய Android மொபைலை இணைக்கவும்.
5. Run அழுத்தவும்.
6. App-ல் `கயிற்றை இயக்கவும்` அழுத்தி, Android காட்டும் "Display over other apps" அனுமதியை வழங்கவும்.

## APK உருவாக்குவது
Android Studio-வில்:
Build → Build Bundle(s) / APK(s) → Build APK(s)

APK பொதுவாக `app/build/outputs/apk/debug/app-debug.apk` இடத்தில் கிடைக்கும்.

## பாதுகாப்பு / தனியுரிமை
இந்த prototype கேமராவைப் பயன்படுத்தாது; படம் அல்லது வீடியோ பதிவு செய்யாது; இணைய அனுமதியும் கேட்காது.


## Android Studio இல்லாமல் GitHub Actions மூலம் APK உருவாக்குவது

1. GitHub.com-ல் account உருவாக்கி sign in செய்யவும்.
2. `New repository` தேர்வு செய்து `LuckyDangleAndroid` என்ற repository உருவாக்கவும்.
3. இந்த ZIP-ஐ Windows-ல் Extract செய்யவும்.
4. GitHub repository-யில் `Add file` → `Upload files` அழுத்தவும்.
5. Extract செய்த project-இன் உள்ளடக்கங்களை upload செய்யவும். `.github/workflows/build-apk.yml` உட்பட எல்லா கோப்புகளும் சேர வேண்டும். கோப்புகள் project root-ல் இருக்க வேண்டும்; ZIP கோப்பை மட்டும் upload செய்ய வேண்டாம்.
6. `Commit changes` அழுத்தவும்.
7. `Actions` tab-ஐத் திறக்கவும். `Build Lucky Dangle APK` workflow-ஐத் தேர்வு செய்து `Run workflow` அழுத்தவும். `main` branch-க்கு push செய்தாலும் build தானாகத் தொடங்கும்.
8. Build வெற்றி பெற்றதும் அந்த workflow run-ஐத் திறக்கவும்.
9. `Artifacts` பகுதியில் `LuckyDangle-debug-apk` என்பதைப் பதிவிறக்கவும். ZIP-ஐ extract செய்தால் `app-debug.apk` கிடைக்கும்.
10. APK-ஐ Redmi மொபைலுக்கு அனுப்பி நிறுவவும். Android கேட்கும்போது APK-ஐத் திறக்கும் app-க்கு மட்டும் “Install unknown apps” அனுமதி வழங்கவும்.

Workflow Java 17 மற்றும் Gradle 8.9 மூலம் `:app:assembleDebug` இயக்கி APK-ஐ artifact ஆகப் பதிவேற்றும். அதிகாரப்பூர்வ வழிகாட்டி: https://docs.github.com/en/actions/concepts/workflows-and-actions/workflow-artifacts

குறிப்பு: இது debug APK; Play Store வெளியீட்டுக்கான release signing இதில் இல்லை. Punch-hole கேமராவின் துல்லியமான இடம் மொபைல் மாடலைப் பொறுத்து மாறுவதால் Redmi-யில் சோதித்து offset-ஐச் சரிசெய்ய வேண்டியிருக்கலாம்.
