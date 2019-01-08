/**
 *  Tree Control Adopted from jsTree.
 */

zbjdl.ns("com.zbjdl.common.ui.widgets").Tree = function(options) {
	this.options = {
		"core": {},
		"plugins": ["themes", "json_data", "types", "ui","contextmenu"],
		"json_data": {},
		"themes": {},
		"types": {}
	};
	$.extend(true, this.options, options);
	this.init();
};

com.zbjdl.common.ui.widgets.Tree.prototype = {
	constructor: com.zbjdl.common.ui.widgets.Tree,
	
	/**
	 * Closes all descendants of the node node.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element whose descendants you want closed. If this parameter
	 *            is omitted or set to -1, all nodes in the tree are closed.
	 */
	closeAll: function(node) {
		if (this.$tree) {
			$.jstree._reference(this.$tree).close_all(node);
		}
	},
	
	/**
	 * Closes an open node, so that its children are not visible. If the
	 * animation config option is greater than 0, the children are hidden using
	 * a slide up animation, whose duration is the value of the animation config
	 * option in milliseconds.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element we want closed.
	 * @param skipAnimation:
	 *            If set to true the animation set in the animation config
	 *            option is skipped. Default is false.
	 */
	closeNode: function(node, skipAnimation) {
		if (this.$tree) {
			$.jstree._reference(this.$tree).close_node(node, skipAnimation);
		}
	},
	
	/**
	 * Destroys the instance.
	 */
	destroy: function() {
		if (this.$tree) {
			this.$tree.destroy;
		}
	},
	
	/**
	 * Gets the LI elements representing the children of the passed node.
	 * Returns false on failure (or if the node has no children).
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element within the tree, whose children we want. Use -1 to
	 *            return all root nodes.
	 */
	getChildren: function(node) {
		return $.jstree._reference(this.$tree)._get_children(node);
	},
	
	/**
	 * Gets the LI element representing the node next to the passed node.
	 * Returns false on failure.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element within the tree, whose next sibling we want.
	 * @param strict:
	 *            If set to true only immediate siblings are calculated.
	 *            Otherwise if the node is the last child of its parent this
	 *            function will "jump out" and return the parent's next sibling,
	 *            etc. Default is false.
	 */
	getNext: function(node, strict) {
		return $.jstree._reference(this.$tree)._get_next(node, strict);
	},
	
	/**
	 * Gets the LI element of the node, -1 if the container node is passed, or
	 * false otherwise.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element within the tree.
	 */
	getNode: function(node) {
		return $.jstree._reference(this.$tree)._get_node(node);
	},
	
	/**
	 * Gets the LI element representing the parent of the passed node. Returns
	 * false on failure.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element within the tree, whose parent we want.
	 */
	getParent: function (node) {
		return $.jstree._reference(this.$tree)._get_parent(node);
	},
	
	/**
	 * Return the path to a node, either as an array of IDs or as an array of
	 * node names.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element within the tree, whose path we want.
	 * @param idMode:
	 *            If set to true IDs are returned instead of the names of the
	 *            parents. Default is false.
	 */
	getPath: function(node, idMode) {
		return $.jstree._reference(this.$tree).get_path(node, idMode);
	},
	
	/**
	 * Gets the LI element representing the node previous to the passed node.
	 * Returns false on failure.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element within the tree, whose previous sibling we want.
	 * @param strict:
	 *            If set to true only immediate siblings are calculated.
	 *            Otherwise if the node is the first child of its parent this
	 *            function will "jump out" and return the parent itself. Default
	 *            is false.
	 */
	getPrevious: function(node, strict) {
		return $.jstree._reference(this.$tree)._get_prev(node, strict);
	},
	
	/**
	 * Returns the title of a node.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to
	 *            the element whose title you need.
	 */
	getText: function(node) {
		return this.$tree ? $.jstree._reference(this.$tree).get_text(node) : "";
	},
	
	init: function() {
		var options = this.options;
		var treeId = "#" + options.treeId;
		var defaultThemeUrl = "../css/tree/style.css";
		var defaultIconPath = "../img/tree/";
		var icons = {
			"default": "tree-default.png",
			"branch": "tree-branch.png",
			"root": "tree-root.png"
		};
		this.$tree = $(treeId).jstree({
			"core": {
				"animation": options.core.animation ? options.core.animation : 0,
				"initially_open": options.core.initiallyOpen ? options.core.initiallyOpen : []
			},
			"plugins": options.plugins,
			"json_data": {
				"data": options.jsonData.data ? options.jsonData.data : null,
				"ajax": {
					"url": options.jsonData.url ? options.jsonData.url : null,
					"data": function(node) {
						return {
							"operation": "get_children",
							"id": node.attr ? node.attr("id") : 0
						};
					},
					"success": function(data) {
						return data.nodes;
					}
				}
			},
			"themes": {
				"dots": options.themes.dots ? options.themes.dots : true,
				"icons": options.themes.icons ? options.themes.icons : true,
				"url": options.themes.url ? options.themes.url : defaultThemeUrl
			},
			"types": {
				"max_children": -2,
				"max_depth": -2,
				"valid_children": ["root"],
				"types": {
					"default": {
						"valid_children": "none",
						"icon": {
							"image": options.types.iconDefault ? options.types.iconDefault : defaultIconPath + icons["default"]
						}
					},
					"branch": {
						"valid_children": ["default", "branch"],
						"icon": {
							"image": options.types.iconBranch ? options.types.iconBranch : defaultIconPath + icons["branch"]
						}
					},
					"root": {
						"valid_children": ["default", "branch"],
						"icon": {
							"image": options.types.iconRoot ? options.types.iconRoot : defaultIconPath + icons["root"]
						}
					}
				}
			},
		    "contextmenu" : {
		    	"items" : {
		    		"viewDetail" : {
		    			"separator_before" : false,
                        "separator_after" : false,
                        "icon" : false,
                        "label" : "编辑菜单",
                        "action" : showEditMenuDialog
		    		},
		    		"createSiblingMenu" : {
		    			"separator_before" : false,
                        "separator_after" : false,
                        "icon" : false,
                        "label" : "添加同级菜单",
                        "action" : showAddSiblingMenuDialog
		    		},
		    		"createSubMenu" : {
		    			"separator_before" : false,
                        "separator_after" : false,
                        "icon" : false,
                        "label" : "添加子菜单",
                        "action" : showAddSubMenuDialog
		    		},
		    		"remove" : {
		    			"separator_before" : false,
                        "separator_after" : false,
                        "icon" : false,
                        "label" : "删除菜单",
                        "action" : removeMenu
		    		},
		    		"moveUp" : {
		    			"separator_before" : false,
                        "separator_after" : false,
                        "icon" : false,
                        "label" : "向上移动",
                        "action" : moveUpMenu
		    		},
		    		"moveDown" : {
		    			"separator_before" : false,
                        "separator_after" : false,
                        "icon" : false,
                        "label" : "向下移动",
                        "action" : moveDownMenu
		    		},
		    		"create" : null,
		    		"rename" : null,
		    		"ccp" : null
		    	}
		    }
		})
	},
	
	/**
	 * Checks if the node is closed.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element you want checked.
	 */
	isClosed: function(node) {
		return this.$tree ? $.jstree._reference(this.$tree).is_closed(node) : false;
	},
	
	/**
	 * Checks if the node is a leaf.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element you want checked.
	 */
	isLeaf: function(node) {
		return this.$tree ? $.jstree._reference(this.$tree).is_leaf(node) : false;
	},
	
	/**
	 * Checks if the node is open.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element you want checked.
	 */
	isOpen: function(node) {
		return this.$tree ? $.jstree._reference(this.$tree).is_open(node) : false;
	},
	
	/**
	 * Opens all descendants of the node node.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element whose descendants you want opened. If this parameter
	 *            is omitted or set to -1, all nodes in the tree are opened.
	 */
	openAll: function(node) {
		if (this.$tree) {
			$.jstree._reference(this.$tree).open_all(node);
		}
	},
	
	/**
	 * Opens a closed node, so that its children are visible. If the animation
	 * config option is greater than 0, the children are revealed using a slide
	 * down animation, whose duration is the value of the animation option in
	 * milliseconds.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element we want opened.
	 * @param skipAnimation:
	 *            If set to true the animation set in the animation config
	 *            option is skipped. Default is false.
	 */
	openNode: function(node, skipAnimation) {
		if (this.$tree) {
			$.jstree._reference(this.$tree).open_node(node, false, skipAnimation);
		}
	},
	
	/**
	 * Refreshes the tree. Saves all open nodes, and reloads and then reopens
	 * all saved nodes.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element within the tree. If set this will reload only the
	 *            given node - otherwise - the whole tree. Passing -1 also
	 *            reloads the whole tree.
	 */
	refresh: function(node) {
		if (this.$tree) {
			$.jstree._reference(this.$tree).refresh(node);
		}
	},
	
	/**
	 * If a node is closed - this function opens it, if it is open - calling
	 * this function will close it.
	 * 
	 * @param node:
	 *            This can be a DOM node, jQuery node or selector pointing to an
	 *            element we want toggled.
	 */
	toggleNode: function(node) {
		if (this.$tree) {
			$.jstree._reference(this.$tree).toggle_node(node);
		}
	}
};
