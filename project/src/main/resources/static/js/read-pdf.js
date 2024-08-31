onload = function () {
    document.getElementById('pagin-table').onclick = function (ev) {
        var r, c, el, year, author, link, tdoc, ist, tannot, keyw;
        ev = ev || event;
        el = ev.target || event.srcElement;
        r = el.parentNode.rowIndex || el.parentElement.rowIndex; //r = el.parentNode.rowIndex - 1 || el.parentElement.rowIndex - 1;
        c = el.cellIndex;
        el = document.getElementById('pagin-table');
        tdoc = el.rows[r].cells[0].innerHTML;
        year = el.rows[r].cells[3].innerHTML;
        author = el.rows[r].cells[2].innerHTML;
        link = el.rows[r].cells[4].innerHTML; //путь
        ist = el.rows[r].cells[5].innerHTML;
        tannot = el.rows[r].cells[9].innerHTML;
        keyw = el.rows[r].cells[6].innerHTML;

        document.getElementById('year').textContent = year;
        document.getElementById('author').textContent = author;
        document.getElementById('tdoc').textContent = tdoc;
        document.getElementById('link').textContent = link;
        document.getElementById('istochnik').textContent = ist;
        document.getElementById('tannot').textContent = tannot;
        document.getElementById('keyword').textContent = keyw;
    };
};