require "x_base_class"

x_sub_class = class(x_base_class);

function x_sub_class:ctor()
    print("&&&x_sub_class ctor");
end

function x_sub_class:hello()
    print("&&&hello x_sub_class");
end

