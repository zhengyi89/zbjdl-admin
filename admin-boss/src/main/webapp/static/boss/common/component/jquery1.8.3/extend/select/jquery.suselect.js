;(function($) {
	$.fn.suselect = function(option){
		var setting = {
			single: true,
			cols: 1,
			optionWidth: 100,
			scrollWidth: 22
		};
		if(option) {
			$.extend(setting, option);
		}
		var getAttrs = function(obj, rels){
			var attrs = {};
			for(var i = 0; i < rels.length; i++) {
				attrs[rels[i] + ""] = obj.attr(rels[i]);
			}
			return attrs;
		}
		return this.each(function(){
			var $this = $(this);
			if($this.attr("jQuery.suselect")) {
				return $this;
			}
			var key = "SUSelect_" + new Date().getTime();
			$this.attr("jQuery.suselect", key);
			var name = $this.attr("name");
			var rels = $this.attr("rel");
			if(rels) {
				rels = rels.split(",");
			} else {
				rels = [];
			}
			$this.removeAttr("name");
			var c = $('<div class="option"><div class="option_btn"><div class="show_area"></div></div><div class="op_box" style="display:none"><ul class="op_list" style="padding:0"></ul></div></div>');
			$this.after(c);
			$this.hide();
			var single = setting.single;
			var cols = setting.cols;
			var panel = c.find("ul.op_list");
			var options = [];
			var onHide = $this.attr("change");
			var box = c.find('div.op_box');
			var w = setting.cols * (setting.optionWidth + 3) + setting.scrollWidth;
			var ow = setting.optionWidth;
			if(w < 164) {
				ow = (164 - setting.scrollWidth) / cols;
				w = 164;
			}
			var suselect = new SUSelect($this, single, cols, ow, name, panel, options, onHide, rels);
			$this.data("suselect", suselect);
			var currents = [];
			box.css("width", w + "px").bgiframe();
			c.find("div.option_btn").click(function(){
				if(box.is(":hidden")){
					suselect.show();
				} else {
					suselect.hide();
				}
			});
			if($this.get(0).tagName == "SELECT") {
			/*	
				$this.children().each(function(){
					var o = $(this);
					if(o.attr('su-selected') == 'selected') {
						currents[currents.length] = o.val();
					}
					suselect.addOption(o.val() || o.html(), o.html(), getAttrs(o, rels));
				});
			*/				
				var options = $this[0].options;
				for(var i=0; i<options.length; i++) {
					var o = $(options[i]);
					if(o.attr('su-selected') == 'selected') {
						currents[currents.length] = o.val();
					}
					suselect.addOption(o.val() || o.html(), o.html(), getAttrs(o, rels));
				}
			} else if($this.get(0).tagName == "UL") {
				$this.children().each(function(){
					var o = $(this);
					if(o.hasClass("current")) {
						currents[currents.length] = o.val();
					}
					suselect.addOption(o.val() || o.html(), o.html(), getAttrs(o, rels));
				});
			}
			suselect.setCurrents(currents);
			return $this;
		});
	}
	
})(jQuery);

SUSelect = function(select, single, cols, width, name, panel, options, onHide, rels){
	this.select = select;
	this.single = single;
	this.cols = cols;
	this.panel = panel;
	this.options = options;
	this._onHide = onHide;
	this.name = name;
	this.box = panel.parent();
	this.width = width;
	this.showArea = this.box.parent().find("div.show_area");
	this.rels = rels;
	this.onAction = false;
};

SUSelect.prototype = {
	
	length: function() {
		return this.options.length;
	},
	
	isSingle : function() {
		return this.single;
	},
	
	getCols : function() {
		return this.cols;
	},
	
	hide: function() {
		if(this.onAction)return;
		var s = this;
		this.onHide();
		this.onAction = true;
		this.box.slideUp("normal", function(){
			s.onAction = false;
		});
	},
	
	show: function() {
		if(this.onAction)return;
		var s = this;
		this.onAction = true;
		this.box.slideDown("normal", function(){
			$(document.body).click(function(event){
				var src = event.target || event.srcElement;
				if(!$(src).hasClass("SUSelect_option")) {
					if(s.onAction) return;
					s.hide();
					$(document.body).unbind("click");
				}
			});
			s.onAction = false;
		});
	},
	
	setCurrents : function(cs) {
		if(!cs instanceof Array) {
			var tmp = cs;
			cs = [];
			cs[0] = tmp;
		}
		var html = "";
		if(this.options.length == 0) {
			html = "-- 暂无选项  --  ";
		} else if(cs.length == 0) {
			html = "-- 请选择  --  ";
		} else {
			for(var i = 0; i < this.options.length; i++) {
				if(cs instanceof Array) {
					for(var j = 0; j < cs.length; j++) {
						if(this.options[i].val() == cs[j]) {
							this.options[i].attr("checked", true);
							html += this.options[i].attr("html") + ", ";
						}
					}
				} else {
					if(this.options[i].val() == cs) {
						this.options[i].attr("checked", true);
						html += this.options[i].attr("html") + ", ";
					}
				}
			}
		}
		this.setShowText(html.substring(0, html.length - 2));
	},
	
	flush: function() {
		this.setCurrents([]);
	},
	
	setShowText: function(text) {
		this.showArea.html(text);
	},
	
	getCurrents : function() {
		var cs = null;
		if(this.single) {
			for(var i = 0; i < this.options.length; i++) {
				if(this.options[i].get(0).checked) {
					cs = {text: this.options[i].attr("html"), value: this.options[i].val()};
					for(var j = 0; j < this.rels.length; j++) {
						cs[this.rels[j]] = this.options[i].attr(this.rels[j]);
					}
					break;
				}
			}
		} else {
			cs = [];
			for(var i = 0; i < this.options.length; i++) {
				if(this.options[i].get(0).checked) {
					cs[cs.length] = {text: this.options[i].attr("html"), value: this.options[i].val()};
					for(var j = 0; j < this.rels.length; j++) {
						cs[cs.length][this.rels[j]] = this.options[i].attr(rels[j]);
					}
				}
			}
		}
		
		return cs;
	},
	
	addOption : function(value, html, attrs) {
		var id = "SUSelect_option_tmp_" + new Date().getTime();
		var liHtml = '<li class="SUSelect_option"><input class="SUSelect_option" name="' + this.name + '" id="' + id + '" html="' + html + '" type="' + (this.isSingle() ? 'radio' : 'checkbox') + '" value="' + value + '"/>' + html + '</li>';
		var li = $(liHtml).css("width", this.width + "px").appendTo(this.panel).hover(function(){
			$(this).addClass("current");
		}, function(){
			$(this).removeClass("current");
		});
		var opt = $("#" + id);
		var single = this.isSingle();
		var s = this;
		for(var key in attrs) {
			opt.attr(key, attrs[key])
		}
		li.click(function(event){
			var vs = [];
			var src = event.target || event.srcElement;
			if(src.tagName == 'LI') {
				if(single){
					opt.get(0).checked = true;
				} else {
					opt.get(0).checked = !opt.get(0).checked;
				}
			}
			s.box.find("input:checked").each(function(i){
				vs[i] = $(this).val();
			});
			s.setCurrents(vs);
			if(single && $(src).hasClass("SUSelect_option")) {
				s.hide();
				$(document.body).unbind("click");
			}
		});
		this.options[this.options.length] = opt;
		opt.removeAttr("id");
		this.flush();
	},
	
	removeOption : function(i) {
		var r = this.options[i];
		r.parent().remove();
		this.options.splice(i, 1);
		var currents = [];
		this.box.find("[input:checked]").each(function(){
			var o = $(this);
			if(o.is(":selected")) {
				currents[currents.length] = o.val();
			}
		});
		this.setCurrents(currents);
	},
	
	onHide: function() {
		if(this._onHide) {
			var values = this.getCurrents();
			if(values) {
				var sel = this.select;
				eval(this._onHide + "(values, sel);");
			}
		}
	}
};