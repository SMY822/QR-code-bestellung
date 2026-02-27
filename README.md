# QR-Code-Bestellsystem (Desktop-Version)

## Projektbeschreibung
Dieses Projekt ist ein desktop-basiertes Restaurant-Bestellsimulationssystem.  
Es zeigt, wie Kunden durch Scannen eines QR-Codes Bestellungen aufgeben können, ohne mit dem Personal zu interagieren.  
Das System dient hauptsächlich zu Lern- und Demonstrationszwecken.

**中文翻译**：  
这是一个基于桌面的餐厅订餐模拟系统，演示顾客如何通过扫描二维码下单，无需与服务人员互动。该系统主要用于学习和演示目的。

---

## Bestellablauf
1. QR-Code scannen, um ins System zu gelangen
2. Speisen aus der Karte auswählen
3. Bestellung bestätigen
4. Zahlungsmethode wählen
5. Auf die Zubereitung warten
6. Benachrichtigung "Gericht bereit" erhalten
7. Bewertung abgeben
8. Sitzung beenden

**中文翻译**：
1. 扫描二维码进入系统
2. 从菜单选择菜品
3. 确认订单
4. 选择支付方式
5. 等待制作
6. 收到“菜品已上齐”通知
7. 提交评价
8. 结束会话

---

## Hauptfunktionen
- QR-Code-Eintrittssimulation
- Menü durchsuchen
- Bestellung erstellen
- Zahlungsmethode auswählen
- Bestellverarbeitung simulieren
- Bewertung abgeben
- Mehrsprachige Unterstützung (Englisch / Deutsch / Chinesisch)

**中文翻译**：
- 二维码进入模拟
- 浏览菜单
- 创建订单
- 选择支付方式
- 模拟订单处理
- 提交评价
- 多语言支持（英语/德语/中文）

---

## Verwendete Technologien
- Java
- Swing
- Maven
- ZXing (QR-Code-Generierung)
- JUnit (Tests)

**中文翻译**：
- Java
- Swing
- Maven
- ZXing（二维码生成）
- JUnit（测试）

---

## Projektstruktur
Das Projekt folgt einer geschichteten Architektur:
- **UI-Ebene**: EntrancePanel, MenuPanel, PaymentPanel, ProcessingPanel, MealReadyPanel, RatingPanel, GoodbyePanel, Navigator
- **Entitätsebene**: Order, OrderItem, FoodItem, Category
- **Service-Ebene**: QRCodeGenerator, LanguageManager, RatingManager
- **Exception**: NoQRScannedException

**中文翻译**：  
项目采用分层架构：
- UI层：EntrancePanel, MenuPanel, PaymentPanel, ProcessingPanel, MealReadyPanel, RatingPanel, GoodbyePanel, Navigator
- 实体层：Order, OrderItem, FoodItem, Category
- 服务层：QRCodeGenerator, LanguageManager, RatingManager
- 异常类：NoQRScannedException

---

## UML-Klassendiagramm
![UML-Klassendiagramm](docs/uml.png)  
*(Das Diagramm zeigt die Kernklassen und ihre Beziehungen.)*

**中文翻译**：  
![UML类图](docs/uml.png)  
（该图显示了核心类及其关系。）

---

## Ausführungshinweise
1. Stellen Sie sicher, dass **Java 11+** und **Maven** installiert sind.
2. Klonen Sie das Repository und navigieren Sie zum Projektverzeichnis.
3. Bauen Sie das Projekt: mvn clean compile
4. Führen Sie die Anwendung aus:mvn exec:java -Dexec.mainClass="com.restaurant.desktop.MainApp"

Oder öffnen Sie das Projekt in IntelliJ IDEA und starten Sie `MainApp.java`.

**中文翻译**：
1. 确保已安装 Java 11+ 和 Maven。
2. 克隆仓库并进入项目根目录。
3. 构建项目：`mvn clean compile`
4. 运行应用：`mvn exec:java -Dexec.mainClass="com.restaurant.desktop.MainApp"` 或在 IntelliJ IDEA 中直接运行 `MainApp.java`。

---

## Verwendete SE2-Konzepte
- **Objekte und Klassen**: Alle Entitäts- und UI-Klassen.
- **Collections und Generics**: `List<FoodItem>`, `Map<Category, List<FoodItem>>`, `ButtonGroup`.
- **Exceptions**: Eigene `NoQRScannedException`, try-catch für QR-Generierung und Eingabevalidierung.
- **GUI (Swing)**: Vollständige Oberfläche mit mehreren Panels und Navigation.
- **Streams**: Wird in `Order.getTotalAmount()` zur Berechnung der Gesamtsumme verwendet.
- **Datei-I/O**: Bewertungen werden über `RatingManager` in `ratings.txt` gespeichert.
- **Enums**: `Category`-Enum mit mehrsprachigen Anzeigenamen.
- **ResourceBundle**: `LanguageManager` für i18n-Unterstützung (Chinesisch, Englisch, Deutsch).
- **Threads**: `SwingUtilities.invokeLater` gewährleistet Thread-Sicherheit; `SwingWorker` in `ProcessingPanel` simuliert das Kochen (optional).
- **Logging**: Einfache `System.out`-Ausgaben; könnte mit `java.util.logging` verbessert werden.
- **Testing**: JUnit-Tests für die `Order`-Klasse (siehe `src/test/java`).

**中文翻译**：
- **对象和类**：所有实体和 UI 类。
- **集合与泛型**：`List<FoodItem>`, `Map<Category, List<FoodItem>>`, `ButtonGroup`。
- **异常处理**：自定义 `NoQRScannedException`，try-catch 处理二维码生成和输入验证。
- **GUI (Swing)**：完整的界面及导航。
- **Streams**：在 `Order.getTotalAmount()` 中用于计算总价。
- **文件 I/O**：通过 `RatingManager` 将评价保存到 `ratings.txt`。
- **枚举**：`Category` 枚举带多语言显示名称。
- **资源包**：`LanguageManager` 实现国际化（中文、英语、德语）。
- **线程**：`SwingUtilities.invokeLater` 保证线程安全；`ProcessingPanel` 中的 `SwingWorker` 模拟制作过程（可选）。
- **日志**：使用简单的 `System.out`，可用 `java.util.logging` 增强。
- **测试**：针对 `Order` 类的 JUnit 测试（见 `src/test/java`）。

---

## Aufgabenverteilung
Siehe `Bewertungsbogen.xlsx` für die detaillierte Aufgabenverteilung im Team.

**中文翻译**：  
分工详情见 `Bewertungsbogen.xlsx`。

---

## Lernnotizen (Nachdenkzettel)
Persönliche Lernnotizen befinden sich im Verzeichnis `nachdenkzettel/`.

**中文翻译**：  
个人学习笔记位于 `nachdenkzettel/` 目录下。

---

## Hinweis
Dieses Projekt wird ausschließlich für akademische Zwecke entwickelt.

**中文翻译**：  
本项目仅用于学术目的。