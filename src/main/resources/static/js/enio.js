/*
 * enio v1.0.0 2017 jjeom87 MIT Licensed
 */
(function(enio, $, window, document) {
  "use strict";
  
  enio.createNS = function(namespace) {
    var nsparts = namespace.split(".");
    var parent = enio;

    if (nsparts[0] === "enio") {
      nsparts = nsparts.slice(1);
    }

    for (var i = 0; i < nsparts.length; i++) {
      var partname = nsparts[i];
      if (typeof parent[partname] === "undefined") {
        parent[partname] = {};
      }
      parent = parent[partname];
    }
    return parent;
  };
}(window.enio = window.enio || {}, window.jQuery, window, document));