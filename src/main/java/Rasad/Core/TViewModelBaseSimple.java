package Rasad.Core;

import main.java.Rasad.Core.IEditingItem;
import main.java.Rasad.Core.TViewModelBase;

public abstract class TViewModelBaseSimple<T extends IEditingItem> extends TViewModelBase
{
	public TViewModelBaseSimple()
	{
		setItems(new ObservableCollection<T>());
		getCommands().AddCommand("Insert", AddNewItem, () -> !getIsInEditMode() && getAllowAddNew());
		getCommands().AddCommand("Edit", Edit, () -> !getIsInEditMode() && getSelectedItem() != null && getAllowEdit());
		getCommands().AddCommand("Delete", Delete, () -> !getIsInEditMode() && getSelectedItem() != null && getAllowEdit());
		getCommands().AddCommand("Save", Save, () -> getIsInEditMode());
		getCommands().AddCommand("Cancel", Cancel, () -> getIsInEditMode());
		getCommands().AddCommand("Reload", Reload, () -> !getIsInEditMode());
		setAllowAddNew(true);
		setAllowEdit(true);
		//Reload();
	}

	public abstract void Reload();

	private boolean AllowAddNew;
	public boolean getAllowAddNew()
	{
		return AllowAddNew;
	}
	protected void setAllowAddNew(boolean value)
	{
		AllowAddNew = value;
	}
	private boolean AllowEdit;
	public boolean getAllowEdit()
	{
		return AllowEdit;
	}
	protected void setAllowEdit(boolean value)
	{
		AllowEdit = value;
	}


	protected void Edit()
	{
		setEditorState(EditorState.Edit);
	}

	protected void Delete()
	{
		IEditingItem deletingItem = getSelectedItem();
		T preItem = getItems().PreviousOf(deletingItem);
		if (ConfirmDeleteItem(deletingItem) && deletingItem.Delete())
		{
			getItems().Remove(deletingItem);
			setSelectedItem((preItem != null) ? preItem : getItems().FirstOrDefault());
		}
	}

	public boolean ConfirmDeleteItem(T item)
	{
		return true;
	}

	protected void Save()
	{
		if (getSelectedItem().SaveChanges())
		{
			setEditorState(Core.EditorState.View);
		}
	}

	protected void Cancel()
	{
		if (getEditorState() == Core.EditorState.Insert)
		{
			getItems().Remove(getSelectedItem());
		}
		else if (getEditorState() == Core.EditorState.Edit)
		{
			if (!getSelectedItem().Reload())
			{
				getItems().Remove(getSelectedItem());
			}
		}
		setEditorState(Core.EditorState.View);
	}

	protected void AddNewItem()
	{
		T newItem = System.Activator.<T>CreateInstance();
		InitializeNewItem(newItem);
		getItems().Add(newItem);
		setSelectedItem(newItem);
		setEditorState(Core.EditorState.Insert);
	}
	protected void InitializeNewItem(T item)
	{

	}

	private ObservableCollection<T> Items;
	public final ObservableCollection<T> getItems()
	{
		return Items;
	}
	private void setItems(ObservableCollection<T> value)
	{
		Items = value;
	}
	public final T getSelectedItem()
	{
		return this.<T>GetValue();
	}
	public final void setSelectedItem(T value)
	{
		if (getEditorState() == Core.EditorState.View)
		{
			if (SetValue(value))
			{
				getCommands().Update();
				OnSelectedItemChanged();
			}
		}
		else
		{
			OnPropertyChanged(() -> getSelectedItem());
		}
	}

	protected void OnSelectedItemChanged()
	{

	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DefaultValue(EditorState.View)] public EditorState EditorState
	public final EditorState getEditorState()
	{
		return this.<EditorState>GetValue("EditorState");
	}
	public final void setEditorState(EditorState value)
	{
		if (SetValue(value, "EditorState"))
		{
			getCommands().Update();
			OnPropertyChanged(() -> getIsInEditMode());

		}
	}
	public final boolean getIsInEditMode()
	{
		return getEditorState() == Core.EditorState.Insert || getEditorState() == Core.EditorState.Edit;
	}

}