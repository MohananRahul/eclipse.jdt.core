/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.core.dom;

/**
 * Abstract base class of AST nodes that represent annotations.
 * <p>
 * <pre>
 * Annotation:
 *		NormalAnnotation
 *		MarkerAnnotation
 *		SingleMemberAnnotation
 * </pre>
 * </p>
 * <p>
 * Note: Support for annotation metadata is an experimental language feature 
 * under discussion in JSR-175 and under consideration for inclusion
 * in the 1.5 release of J2SE. The support here is therefore tentative
 * and subject to change.
 * </p>
 * @since 3.0
 */
public abstract class Annotation extends ExtendedModifier {
	
	/**
	 * Returns structural property descriptor for the "typeName" property
	 * of this node.
	 * 
	 * @return the property descriptor
	 */
	abstract ChildPropertyDescriptor internalTypeNameProperty();

	/**
	 * Creates and returns a structural property descriptor for the
	 * "typeName" property declared on the given concrete node type.
	 * 
	 * @return the property descriptor
	 */
	static final ChildPropertyDescriptor internalTypeNamePropertyFactory(Class nodeClass) {
		return new ChildPropertyDescriptor(nodeClass, "typeName", Name.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$
	}
	
	/**
	 * The annotation type name; lazily initialized; defaults to an unspecified,
	 * legal Java identifier.
	 */
	Name typeName = null;

	/**
	 * Creates a new AST node for an annotation node owned by the 
	 * given AST.
	 * <p>
	 * N.B. This constructor is package-private.
	 * </p>
	 * 
	 * @param ast the AST that is to own this node
	 */
	Annotation(AST ast) {
		super(ast);
	}
	
	/**
	 * Returns the annotation type name of this annotation.
	 * 
	 * @return the annotation type name
	 */ 
	public Name getTypeName() {
		if (this.typeName == null) {
			preLazyInit();
			this.typeName = new SimpleName(this.ast);
			postLazyInit(this.typeName, internalTypeNameProperty());
		}
		return this.typeName;
	}
	
	/**
	 * Sets the annotation type name of this annotation.
	 * 
	 * @param typeName the annotation type name
	 * @exception IllegalArgumentException if:
	 * <ul>
	 * <li>the node belongs to a different AST</li>
	 * <li>the node already has a parent</li>
	 * </ul>
	 */ 
	public void setTypeName(Name typeName) {
		if (typeName == null) {
			throw new IllegalArgumentException();
		}
		ChildPropertyDescriptor p = internalTypeNameProperty();
		ASTNode oldChild = this.typeName;
		preReplaceChild(oldChild, typeName, p);
		this.typeName = typeName;
		postReplaceChild(oldChild, typeName, p);
	}

	/**
	 * Returns whether this is a normal annotation
	 * ({@link NormalAnnotation}).
	 * 
	 * @return <code>true</code> if this is a normal annotation,
	 *    and <code>false</code> otherwise
	 */ 
	public boolean isNormalAnnotation() {
		return (this instanceof NormalAnnotation);
	}

	/**
	 * Returns whether this is a marker annotation
	 * ({@link MarkerAnnotation}).
	 * 
	 * @return <code>true</code> if this is a marker annotation,
	 *    and <code>false</code> otherwise
	 */ 
	public boolean isMarkerAnnotation() {
		return (this instanceof MarkerAnnotation);
	}

	/**
	 * Returns whether this is a single member annotation.
	 * ({@link SingleMemberAnnotation}).
	 * 
	 * @return <code>true</code> if this is a single member annotation,
	 *    and <code>false</code> otherwise
	 */ 
	public boolean isSingleMemberAnnotation() {
		return (this instanceof SingleMemberAnnotation);
	}

	/* (omit javadoc for this method)
	 * Method declared on ASTNode.
	 */
	int memSize() {
		return BASE_NODE_SIZE + 1 * 4;
	}
}

