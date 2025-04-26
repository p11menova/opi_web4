import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.SAXException

def validateXmlFile(File file) {
    def factory = DocumentBuilderFactory.newInstance()
    factory.setNamespaceAware(true)
    try {
        def builder = factory.newDocumentBuilder()
        builder.parse(file)
        println "✅файл '${file.name}' валиден."
    } catch (SAXException e) {
        println "❌ошибка в файле '${file.name}': ${e.message}"
    } catch (Exception e) {
        println "❌проблема при проверке файла '${file.name}': ${e.message}"
    }
}
