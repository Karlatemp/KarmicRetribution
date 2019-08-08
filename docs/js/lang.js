(function () {
    var lang = String(navigator.language || navigator.userLanguage);
    var lc = lang.split("-");
    var using = {
        message: {
            enable: {
                hp: "Show HP nav",
                karma: "Show karma nav",
                lost: "Show lost nav",
                length: "Total length"
            },
            style: {
                blod: "Using Blod style",
                italic: "Using italic style",
                magic: "Using magic style",
                strikethrough: "Using strikethrough style",
                underline: "Using underline style"
            },
            ending: {
                msg: "The ending char color",
                none: "None",
                black: "Represents black (0)",
                dark_blue: "Represents dark blue (1)",
                dark_green: "Represents dark green (2)",
                dark_aqua: "Represents dark blue (aqua) (3)",
                dark_red: "Represents dark red (4)",
                dark_purple: "Represents dark purple (5)",
                glod: "Represents gold (6)",
                gray: "Represents gray (7)",
                dark_gray: "Represents dark gray (8)",
                blue: "Represents blue (9)",
                green: "Represents green (a)",
                aqua: "Represents aqua (b)",
                red: "Represents red (c)",
                light_purple: "Represents light purple (d)",
                yellow: "Represents yellow (e)",
                white: "Represents white (f)",
                reset: "Resets all previous chat colors or formats. (r)"
            },
            color: {
                a7line: "1234567890abcdef".replace(/./gi, function (cut) {
                    return '\u00a7' + cut + cut
                }),
                essline: "1234567890abcdef".replace(/./gi, function (cut) {
                    return '&' + cut + cut
                })
            },
            text: {
                load: "Load",
                generate: "Generate"
            }
        }
    };
    var langs = {
        zh: {
            message: {
                enable: {
                    hp: "显示正常血量部分",
                    karma: "显示Karma部分",
                    lost: "显示已损失部分",
                    length: "总长度"
                },
                style: {
                    blod: "使用粗体",
                    italic: "使用斜体",
                    magic: "使用乱码 (k)",
                    strikethrough: "使用删除线",
                    underline: "使用下划线"
                },
                ending: {
                    msg: "解析结尾的颜色代码",
                    none: "无",
                    reset: "重置所有颜色和格式. (r)"
                },
                text: {
                    load: "加载",
                    generate: "生成"
                }
            }
        }
    };
    function copyto(a, b) {
        for (var i in a) {
            var v = a[i];
            if (typeof v == "object") {
                var k = b[i];
                if (typeof k == "object") {
                    copyto(v, k);
                } else {
                    b[i] = k;
                }
            } else {
                b[i] = v;
            }
        }
    }
    function load(type) {
        var lg = langs[type];
        if (lg != null) {
            copyto(lg, using);
        }
    }
    load(lc[0].toLowerCase());
    load(lang.toLowerCase());
    using = peek(using);
    function peek(mx) {
        var copyed = {};
        for (var i in mx) {
            var v = mx[i];
            if (typeof v == "object") {
                var peeked = peek(v);
                for (var k in peeked) {
                    copyed[i + "." + k] = peeked[k];
                }
            } else {
                copyed[i] = v;
            }
        }
        return copyed;
    }
    console.log(using);
    function parse(line) {
        var l = String(line);
        var result = "";
        var length = l.length;
        for (var i = 0; i < length; i++) {
            var c = l.charAt(i);
            if (c == "$") {
                i++;
                if (l.charAt(i) == "{") {
                    var ending = l.indexOf("}", i);
                    if (ending < 0) {
                        result += "$" + l.substring(i);
                        break;
                    } else {
                        var varable = l.substring(i + 1, ending);
                        i = ending;
                        if (varable in using) {
                            result += using[varable];
                        } else {
                            result += "${" + varable + "}";
                        }
                    }
                } else {
                    result += "$" + l.charAt(i + 1);
                }
            } else {
                result += c;
            }
        }
        return result;
    }
    window.ppap = parse;
    function loopw(/** @type {Element} */doc) {
        if (doc instanceof Text) {
            if (doc.textContent.indexOf("$") != -1)
                doc.textContent = parse(doc.textContent);
            return;
        }
        if (doc.hasChildNodes()) {
            var nodes = doc.childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes.item(i);
                loopw(node);
            }
        }
    }
    window.lopp = loopw;
})();