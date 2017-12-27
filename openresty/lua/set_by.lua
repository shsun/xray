--[[

1. fetch json-obj from shared_dic at set_by or rewrite 
2. save an json-obj in shared_dic if step-1 is nil and re-fetch it at set_by or rewrite.
3. return responsed proxy_pass via json-obj.times at set_by or rewrite
4. increase times&body_filter_times at body_filter
5. reset times&body_filter_times at body_filter at log
6. re-visite the url, go-to step-1-->step-3-->step-4-->step-5;

]]

local x = require("x");
return x.sayhi();