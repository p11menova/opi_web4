static def convertToUnicode(String text) {
    def sb = new StringBuilder()
    text.each { ch ->
        int code = (int) ch
        if (code < 0x20 || code > 0x7E) {
            def hex = Integer.toHexString(code).padLeft(4, '0')
            sb.append("\\u${hex}")
        } else {
            sb.append(ch)
        }
    }
    return sb.toString()
}


return this.&convertToUnicode
