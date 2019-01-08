/**
 * Yeepay ui namespace define
 */
function $_yeepay_namespace() {
    var ns, d;
    ns = window;
    if (arguments[0]) {
        d = arguments[0].split(".");
        for (var i = 0; i < d.length; i++) {
            ns = ns[d[i]] = ns[d[i]] ? ns[d[i]] : {};
        }
    }
    return ns;
}
var yeepay = window.yeepay = $_yeepay_namespace("yeepay");
yeepay.ns = $_yeepay_namespace;
