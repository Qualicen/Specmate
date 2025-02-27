/**
 */
package com.specmate.model.base.impl;

import com.specmate.model.administration.AdministrationPackage;

import com.specmate.model.administration.impl.AdministrationPackageImpl;

import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.BasePackage;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IExternal;
import com.specmate.model.base.IModelConnection;
import com.specmate.model.base.IModelNode;
import com.specmate.model.base.INamed;
import com.specmate.model.base.IPositionable;
import com.specmate.model.base.IRecycled;
import com.specmate.model.base.ISpecmateModelObject;
import com.specmate.model.base.ISpecmatePositionableModelObject;
import com.specmate.model.base.ITracingElement;

import com.specmate.model.base.ModelImage;
import com.specmate.model.batch.BatchPackage;

import com.specmate.model.batch.impl.BatchPackageImpl;

import com.specmate.model.export.ExportPackage;

import com.specmate.model.export.impl.ExportPackageImpl;

import com.specmate.model.history.HistoryPackage;

import com.specmate.model.history.impl.HistoryPackageImpl;

import com.specmate.model.processes.ProcessesPackage;

import com.specmate.model.processes.impl.ProcessesPackageImpl;

import com.specmate.model.requirements.RequirementsPackage;

import com.specmate.model.requirements.impl.RequirementsPackageImpl;

import com.specmate.model.testspecification.TestspecificationPackage;

import com.specmate.model.testspecification.impl.TestspecificationPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BasePackageImpl extends EPackageImpl implements BasePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNamedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iDescribedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iidEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iContentElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSpecmateModelObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass folderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iPositionableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iExternalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSpecmatePositionableModelObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iModelConnectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iModelNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTracingElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iRecycledEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelImageEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.specmate.model.base.BasePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BasePackageImpl() {
		super(eNS_URI, BaseFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link BasePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BasePackage init() {
		if (isInited) return (BasePackage)EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBasePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BasePackageImpl theBasePackage = registeredBasePackage instanceof BasePackageImpl ? (BasePackageImpl)registeredBasePackage : new BasePackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(RequirementsPackage.eNS_URI);
		RequirementsPackageImpl theRequirementsPackage = (RequirementsPackageImpl)(registeredPackage instanceof RequirementsPackageImpl ? registeredPackage : RequirementsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TestspecificationPackage.eNS_URI);
		TestspecificationPackageImpl theTestspecificationPackage = (TestspecificationPackageImpl)(registeredPackage instanceof TestspecificationPackageImpl ? registeredPackage : TestspecificationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ProcessesPackage.eNS_URI);
		ProcessesPackageImpl theProcessesPackage = (ProcessesPackageImpl)(registeredPackage instanceof ProcessesPackageImpl ? registeredPackage : ProcessesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(HistoryPackage.eNS_URI);
		HistoryPackageImpl theHistoryPackage = (HistoryPackageImpl)(registeredPackage instanceof HistoryPackageImpl ? registeredPackage : HistoryPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(AdministrationPackage.eNS_URI);
		AdministrationPackageImpl theAdministrationPackage = (AdministrationPackageImpl)(registeredPackage instanceof AdministrationPackageImpl ? registeredPackage : AdministrationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BatchPackage.eNS_URI);
		BatchPackageImpl theBatchPackage = (BatchPackageImpl)(registeredPackage instanceof BatchPackageImpl ? registeredPackage : BatchPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ExportPackage.eNS_URI);
		ExportPackageImpl theExportPackage = (ExportPackageImpl)(registeredPackage instanceof ExportPackageImpl ? registeredPackage : ExportPackage.eINSTANCE);

		// Create package meta-data objects
		theBasePackage.createPackageContents();
		theRequirementsPackage.createPackageContents();
		theTestspecificationPackage.createPackageContents();
		theProcessesPackage.createPackageContents();
		theHistoryPackage.createPackageContents();
		theAdministrationPackage.createPackageContents();
		theBatchPackage.createPackageContents();
		theExportPackage.createPackageContents();

		// Initialize created meta-data
		theBasePackage.initializePackageContents();
		theRequirementsPackage.initializePackageContents();
		theTestspecificationPackage.initializePackageContents();
		theProcessesPackage.initializePackageContents();
		theHistoryPackage.initializePackageContents();
		theAdministrationPackage.initializePackageContents();
		theBatchPackage.initializePackageContents();
		theExportPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBasePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BasePackage.eNS_URI, theBasePackage);
		return theBasePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getINamed() {
		return iNamedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getINamed_Name() {
		return (EAttribute)iNamedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIDescribed() {
		return iDescribedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIDescribed_Description() {
		return (EAttribute)iDescribedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIID() {
		return iidEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIID_Id() {
		return (EAttribute)iidEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIContentElement() {
		return iContentElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIContainer() {
		return iContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIContainer_Contents() {
		return (EReference)iContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getISpecmateModelObject() {
		return iSpecmateModelObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFolder() {
		return folderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFolder_Library() {
		return (EAttribute)folderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIPositionable() {
		return iPositionableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIPositionable_Position() {
		return (EAttribute)iPositionableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIExternal() {
		return iExternalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIExternal_ExtId() {
		return (EAttribute)iExternalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIExternal_ExtId2() {
		return (EAttribute)iExternalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIExternal_Source() {
		return (EAttribute)iExternalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIExternal_Live() {
		return (EAttribute)iExternalEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getISpecmatePositionableModelObject() {
		return iSpecmatePositionableModelObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getISpecmatePositionableModelObject_X() {
		return (EAttribute)iSpecmatePositionableModelObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getISpecmatePositionableModelObject_Y() {
		return (EAttribute)iSpecmatePositionableModelObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getISpecmatePositionableModelObject_Width() {
		return (EAttribute)iSpecmatePositionableModelObjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getISpecmatePositionableModelObject_Height() {
		return (EAttribute)iSpecmatePositionableModelObjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIModelConnection() {
		return iModelConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIModelConnection_Source() {
		return (EReference)iModelConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIModelConnection_Target() {
		return (EReference)iModelConnectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIModelNode() {
		return iModelNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIModelNode_OutgoingConnections() {
		return (EReference)iModelNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIModelNode_IncomingConnections() {
		return (EReference)iModelNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getITracingElement() {
		return iTracingElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getITracingElement_TracesTo() {
		return (EReference)iTracingElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getITracingElement_TracesFrom() {
		return (EReference)iTracingElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIRecycled() {
		return iRecycledEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIRecycled_Recycled() {
		return (EAttribute)iRecycledEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIRecycled_HasRecycledChildren() {
		return (EAttribute)iRecycledEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModelImage() {
		return modelImageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModelImage_ImageData() {
		return (EAttribute)modelImageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BaseFactory getBaseFactory() {
		return (BaseFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		iNamedEClass = createEClass(INAMED);
		createEAttribute(iNamedEClass, INAMED__NAME);

		iDescribedEClass = createEClass(IDESCRIBED);
		createEAttribute(iDescribedEClass, IDESCRIBED__DESCRIPTION);

		iidEClass = createEClass(IID);
		createEAttribute(iidEClass, IID__ID);

		iContentElementEClass = createEClass(ICONTENT_ELEMENT);

		iContainerEClass = createEClass(ICONTAINER);
		createEReference(iContainerEClass, ICONTAINER__CONTENTS);

		iSpecmateModelObjectEClass = createEClass(ISPECMATE_MODEL_OBJECT);

		folderEClass = createEClass(FOLDER);
		createEAttribute(folderEClass, FOLDER__LIBRARY);

		iPositionableEClass = createEClass(IPOSITIONABLE);
		createEAttribute(iPositionableEClass, IPOSITIONABLE__POSITION);

		iExternalEClass = createEClass(IEXTERNAL);
		createEAttribute(iExternalEClass, IEXTERNAL__EXT_ID);
		createEAttribute(iExternalEClass, IEXTERNAL__EXT_ID2);
		createEAttribute(iExternalEClass, IEXTERNAL__SOURCE);
		createEAttribute(iExternalEClass, IEXTERNAL__LIVE);

		iSpecmatePositionableModelObjectEClass = createEClass(ISPECMATE_POSITIONABLE_MODEL_OBJECT);
		createEAttribute(iSpecmatePositionableModelObjectEClass, ISPECMATE_POSITIONABLE_MODEL_OBJECT__X);
		createEAttribute(iSpecmatePositionableModelObjectEClass, ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y);
		createEAttribute(iSpecmatePositionableModelObjectEClass, ISPECMATE_POSITIONABLE_MODEL_OBJECT__WIDTH);
		createEAttribute(iSpecmatePositionableModelObjectEClass, ISPECMATE_POSITIONABLE_MODEL_OBJECT__HEIGHT);

		iModelConnectionEClass = createEClass(IMODEL_CONNECTION);
		createEReference(iModelConnectionEClass, IMODEL_CONNECTION__SOURCE);
		createEReference(iModelConnectionEClass, IMODEL_CONNECTION__TARGET);

		iModelNodeEClass = createEClass(IMODEL_NODE);
		createEReference(iModelNodeEClass, IMODEL_NODE__OUTGOING_CONNECTIONS);
		createEReference(iModelNodeEClass, IMODEL_NODE__INCOMING_CONNECTIONS);

		iTracingElementEClass = createEClass(ITRACING_ELEMENT);
		createEReference(iTracingElementEClass, ITRACING_ELEMENT__TRACES_TO);
		createEReference(iTracingElementEClass, ITRACING_ELEMENT__TRACES_FROM);

		iRecycledEClass = createEClass(IRECYCLED);
		createEAttribute(iRecycledEClass, IRECYCLED__RECYCLED);
		createEAttribute(iRecycledEClass, IRECYCLED__HAS_RECYCLED_CHILDREN);

		modelImageEClass = createEClass(MODEL_IMAGE);
		createEAttribute(modelImageEClass, MODEL_IMAGE__IMAGE_DATA);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iContentElementEClass.getESuperTypes().add(this.getIID());
		iContentElementEClass.getESuperTypes().add(this.getINamed());
		iContentElementEClass.getESuperTypes().add(this.getIDescribed());
		iContentElementEClass.getESuperTypes().add(this.getIRecycled());
		iContainerEClass.getESuperTypes().add(this.getIContentElement());
		iSpecmateModelObjectEClass.getESuperTypes().add(this.getIContainer());
		iSpecmateModelObjectEClass.getESuperTypes().add(this.getITracingElement());
		folderEClass.getESuperTypes().add(this.getISpecmateModelObject());
		iSpecmatePositionableModelObjectEClass.getESuperTypes().add(this.getISpecmateModelObject());
		iModelConnectionEClass.getESuperTypes().add(this.getISpecmateModelObject());
		iModelNodeEClass.getESuperTypes().add(this.getISpecmatePositionableModelObject());
		modelImageEClass.getESuperTypes().add(this.getIContentElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(iNamedEClass, INamed.class, "INamed", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getINamed_Name(), ecorePackage.getEString(), "name", null, 0, 1, INamed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iDescribedEClass, IDescribed.class, "IDescribed", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIDescribed_Description(), ecorePackage.getEString(), "description", null, 0, 1, IDescribed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iidEClass, com.specmate.model.base.IID.class, "IID", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIID_Id(), ecorePackage.getEString(), "id", null, 0, 1, com.specmate.model.base.IID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iContentElementEClass, IContentElement.class, "IContentElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iContainerEClass, IContainer.class, "IContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIContainer_Contents(), this.getIContentElement(), null, "contents", null, 0, -1, IContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iSpecmateModelObjectEClass, ISpecmateModelObject.class, "ISpecmateModelObject", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(folderEClass, Folder.class, "Folder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFolder_Library(), ecorePackage.getEBoolean(), "library", null, 0, 1, Folder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iPositionableEClass, IPositionable.class, "IPositionable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIPositionable_Position(), ecorePackage.getEInt(), "position", null, 0, 1, IPositionable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iExternalEClass, IExternal.class, "IExternal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIExternal_ExtId(), ecorePackage.getEString(), "extId", null, 0, 1, IExternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIExternal_ExtId2(), ecorePackage.getEString(), "extId2", null, 0, 1, IExternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIExternal_Source(), ecorePackage.getEString(), "source", null, 0, 1, IExternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIExternal_Live(), ecorePackage.getEBoolean(), "live", null, 0, 1, IExternal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iSpecmatePositionableModelObjectEClass, ISpecmatePositionableModelObject.class, "ISpecmatePositionableModelObject", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getISpecmatePositionableModelObject_X(), ecorePackage.getEDouble(), "x", null, 0, 1, ISpecmatePositionableModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getISpecmatePositionableModelObject_Y(), ecorePackage.getEDouble(), "y", null, 0, 1, ISpecmatePositionableModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getISpecmatePositionableModelObject_Width(), ecorePackage.getEDouble(), "width", null, 0, 1, ISpecmatePositionableModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getISpecmatePositionableModelObject_Height(), ecorePackage.getEDouble(), "height", null, 0, 1, ISpecmatePositionableModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iModelConnectionEClass, IModelConnection.class, "IModelConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIModelConnection_Source(), this.getIModelNode(), this.getIModelNode_OutgoingConnections(), "source", null, 0, 1, IModelConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIModelConnection_Target(), this.getIModelNode(), this.getIModelNode_IncomingConnections(), "target", null, 0, 1, IModelConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iModelNodeEClass, IModelNode.class, "IModelNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIModelNode_OutgoingConnections(), this.getIModelConnection(), this.getIModelConnection_Source(), "outgoingConnections", null, 0, -1, IModelNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIModelNode_IncomingConnections(), this.getIModelConnection(), this.getIModelConnection_Target(), "incomingConnections", null, 0, -1, IModelNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iTracingElementEClass, ITracingElement.class, "ITracingElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getITracingElement_TracesTo(), this.getITracingElement(), this.getITracingElement_TracesFrom(), "tracesTo", null, 0, -1, ITracingElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getITracingElement_TracesFrom(), this.getITracingElement(), this.getITracingElement_TracesTo(), "tracesFrom", null, 0, -1, ITracingElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iRecycledEClass, IRecycled.class, "IRecycled", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIRecycled_Recycled(), ecorePackage.getEBoolean(), "recycled", null, 0, 1, IRecycled.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIRecycled_HasRecycledChildren(), ecorePackage.getEBoolean(), "hasRecycledChildren", null, 0, 1, IRecycled.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelImageEClass, ModelImage.class, "ModelImage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModelImage_ImageData(), ecorePackage.getEString(), "imageData", null, 0, 1, ModelImage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://specmate.com/form_meta
		createForm_metaAnnotations();
		// http://specmate.com/internalAttribute
		createInternalAttributeAnnotations();
		// http://specmate.com/notLoadingOnList
		createNotLoadingOnListAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://specmate.com/form_meta</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createForm_metaAnnotations() {
		String source = "http://specmate.com/form_meta";
		addAnnotation
		  (getINamed_Name(),
		   source,
		   new String[] {
			   "shortDesc", "Name",
			   "longDesc", "",
			   "required", "true",
			   "type", "text",
			   "position", "0",
			   "allowedPattern", "^[^,;|]*$"
		   });
		addAnnotation
		  (getIDescribed_Description(),
		   source,
		   new String[] {
			   "shortDesc", "Description",
			   "longDesc", "",
			   "required", "false",
			   "type", "longText",
			   "rows", "5",
			   "position", "100"
		   });
		addAnnotation
		  (modelImageEClass,
		   source,
		   new String[] {
			   "disabled1", "name",
			   "disabled2", "description"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://specmate.com/internalAttribute</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createInternalAttributeAnnotations() {
		String source = "http://specmate.com/internalAttribute";
		addAnnotation
		  (getIRecycled_Recycled(),
		   source,
		   new String[] {
		   });
		addAnnotation
		  (getIRecycled_HasRecycledChildren(),
		   source,
		   new String[] {
		   });
	}

	/**
	 * Initializes the annotations for <b>http://specmate.com/notLoadingOnList</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createNotLoadingOnListAnnotations() {
		String source = "http://specmate.com/notLoadingOnList";
		addAnnotation
		  (modelImageEClass,
		   source,
		   new String[] {
		   });
	}

} //BasePackageImpl
