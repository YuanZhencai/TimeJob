package com.ibm.filenet.helper.vo;

import com.filenet.api.core.Folder;
import com.filenet.api.util.Id;

public abstract class FolderVo extends EntityVo
{
  public FolderVo fromFolder(Folder folder)
  {
    setId(folder.get_Id().toString());
    setName(folder.get_Name());
    setClassName(folder.getClassName());
    setMimeType("folder");
    setCheckedOut(false);
    setPath(folder.get_PathName().substring(9));
    fromCustom(folder);
    return this;
  }

  protected abstract void fromCustom(Folder paramFolder);
}