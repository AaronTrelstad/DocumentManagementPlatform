import React, { useState } from "react";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Button,
  TextField,
} from "@mui/material";
import axios from "axios";
import { Document } from "../types/document";

export function EditDocumentDialog({
  open,
  onClose,
  document,
  onDocumentUpdate,
}: {
  open: boolean;
  onClose: () => void;
  document: Document;
  onDocumentUpdate: () => void;
}) {
  const [formData, setFormData] = useState({
    name: document.name,
    description: document.description,
  });

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSave = async () => {
    const data = new FormData()
    data.append("name", formData.name)
    data.append("description", formData.description)

    try {
      await axios.patch(`http://localhost:8080/api/documents/${document.id}`, data);
      onDocumentUpdate();
      onClose();
    } catch (error) {
      console.error("Error updating document:", error);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>Edit Document</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Update the name and description of the document.
        </DialogContentText>
        <TextField
          autoFocus
          margin="dense"
          id="name"
          name="name"
          label="Name"
          value={formData.name}
          onChange={handleInputChange}
          fullWidth
          variant="standard"
        />
        <TextField
          margin="dense"
          id="description"
          name="description"
          label="Description"
          value={formData.description}
          onChange={handleInputChange}
          fullWidth
          variant="standard"
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSave} color="primary">
          Save
        </Button>
      </DialogActions>
    </Dialog>
  );
}
