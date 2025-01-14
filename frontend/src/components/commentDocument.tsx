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

export function CommentDocument({
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
    commenterId: "",
    message: "",
  });

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSave = async () => {
    const data = new FormData()
    data.append("commenterId", formData.commenterId)
    data.append("message", formData.message)

    try {
      await axios.patch(`http://localhost:8080/api/documents/${document.id}/comment`, data);
      onDocumentUpdate();
      onClose();
    } catch (error) {
      console.error("Error updating document:", error);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>Add a comment</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Add a comment that can be seen by others
        </DialogContentText>
        <TextField
          margin="dense"
          id="message"
          name="message"
          label="Message"
          value={formData.message}
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
