require "x_class"

x_base_class = class();

function x_base_class:ctor(x)
    print("&&&x_base_class ctor");
    self.x = x;
end

function x_base_class:print_x()
    print("&&&", self.x);
end

function x_base_class:hello()
    print("&&&hello x_base_class");
end
