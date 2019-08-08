(function () {
    var fff = {
        k: 1 << 0,
        h: 1 << 1,
        l: 1 << 2,
        b: 1 << 3,
        i: 1 << 4,
        m: 1 << 5,
        s: 1 << 6,
        u: 1 << 7
    };
    /**
     * @type {HTMLInputElement}
     */
    var hp = document.getElementById("hp");
    /**
     * @type {HTMLInputElement}
     */
    var karma = document.getElementById("karma");
    /**
     * @type {HTMLInputElement}
     */
    var lost = document.getElementById("lost");
    /**
     * @type {HTMLInputElement}
     */
    var blod = document.getElementById("blod");
    /**
     * @type {HTMLInputElement}
     */
    var italic = document.getElementById("italic");
    /**
     * @type {HTMLInputElement}
     */
    var length = document.getElementById("length");
    /**
     * @type {HTMLInputElement}
     */
    var magic = document.getElementById("magic");
    /**
     * @type {HTMLInputElement}
     */
    var strikethrough = document.getElementById("strikethrough");
    /**
     * @type {HTMLInputElement}
     */
    var underline = document.getElementById("underline");
    /**
     * @type {HTMLSelectElement}
     */
    var ending = document.getElementById("ending");
    /**
     * @type {HTMLButtonElement}
     */
    var b_load = document.getElementById("b_load");
    /**
     * @type {HTMLButtonElement}
     */
    var b_gen = document.getElementById("b_gen");
    /**
     * @type {HTMLInputElement}
     */
    var area = document.getElementById("area");
    b_load.addEventListener("click", function () {
        var ll = area.value;
        var llx = ll.split("_");
        length.value = Number(llx[2]);
        var flags = Number(llx[3]);
        if (!isNaN(flags)) {
            underline.checked = (flags & fff.u) != 0;
            karma.checked = (flags & fff.k) != 0;
            hp.checked = (flags & fff.h) != 0;
            blod.checked = (flags & fff.b) != 0;
            italic.checked = (flags & fff.i) != 0;
            magic.checked = (flags & fff.m) != 0;
            strikethrough.checked = (flags & fff.s) != 0;
            lost.checked = (flags & fff.l) != 0;
            var ccc = ((flags >>> 8) & 0xFF) - 1;
            ending.value = ccc;
        }
    });
    b_gen.addEventListener("click", function () {
        var rex = "karmicretribution_nav_" + length.value + "_";
        var flags = 0;
        if (karma.checked) {
            flags |= fff.k;
        }
        if (hp.checked) {
            flags |= fff.h;
        }
        if (lost.checked) {
            flags |= fff.l;
        }
        if (blod.checked) {
            flags |= fff.b;
        }
        if (italic.checked) {
            flags |= fff.i;
        }
        if (magic.checked) {
            flags |= fff.m;
        }
        if (strikethrough.checked) {
            flags |= fff.s;
        }
        if (underline.checked) {
            flags |= fff.u;
        }
        var ed = ending.value;
        if (ed != "-1") {
            flags |= ((Number(ed) + 1) & 0xFF) << 8;
        }
        rex += flags;
        area.value = rex;
    });
})();