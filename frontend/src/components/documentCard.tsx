import {
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  IconButton,
  Typography,
} from "@mui/material";
import FavoriteIcon from "@mui/icons-material/Favorite";
import InsightsIcon from "@mui/icons-material/Insights";
import AssistantIcon from "@mui/icons-material/Assistant";
import AddCommentIcon from "@mui/icons-material/AddComment";
import DeleteIcon from "@mui/icons-material/Delete";
import RemoveRedEyeIcon from "@mui/icons-material/RemoveRedEye";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import { Document } from "../types/document";
import axios from "axios";
import { useState } from "react";
import { EditDocumentDialog } from "./editDocument";

export function DocumentCard({
  document,
  onDocumentChange,
}: {
  document: Document;
  onDocumentChange: () => void;
}) {
  const [editDialogOpen, setEditDialogOpen] = useState(false);

  const handleDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/api/documents/${document.id}`);
      onDocumentChange();
    } catch (error) {
      console.error("Error deleting document:", error);
    }
  };

  const openPDFViewer = () => {
    const binary = atob(document.fileBase64);
    const length = binary.length;
    const buffer = new ArrayBuffer(length);
    const view = new Uint8Array(buffer);

    for (let i = 0; i < length; i++) {
      view[i] = binary.charCodeAt(i);
    }

    const blob = new Blob([buffer], { type: "application/pdf" });
    const url = URL.createObjectURL(blob);
    window.open(url, "_blank");
  };

  const isPDF = (fileBase64: string) => {
    return fileBase64.startsWith("JVBERi0xL");
  };

  const handleEdit = async () => {
    try {
      await axios.patch(
        `http://localhost:8080/api/documents/${document.id}`,
        {}
      );
    } catch (error) {}
  };

  return (
    <>
      <Card sx={{ width: 400, height: 325 }}>
        <CardHeader
          title={document.name}
          action={
            <IconButton aria-label="edit" onClick={() => setEditDialogOpen(true)}>
              <ModeEditIcon />
            </IconButton>
          }
        />

        {isPDF(document.fileBase64) ? (
          <CardMedia
            component="iframe"
            height="140"
            src={`data:application/pdf;base64,${document.fileBase64}`}
            title={`${document.name} PDF`}
          />
        ) : (
          <CardMedia
            component="img"
            height="140"
            image={`data:image/jpeg;base64,${document.fileBase64}`}
            alt={`${document.name} image`}
          />
        )}

        <CardContent>
          <Typography variant="body2" color="text.secondary">
            {document.description}
          </Typography>
        </CardContent>

        <CardActions>
          <IconButton
            aria-label="like"
            sx={{
              "&:hover": {
                color: "red",
              },
            }}
          >
            <FavoriteIcon />
          </IconButton>
          <IconButton
            aria-label="comment"
            sx={{
              "&:hover": {
                color: "lightblue",
              },
            }}
          >
            <AddCommentIcon />
          </IconButton>
          <IconButton
            aria-label="analytics"
            sx={{
              "&:hover": {
                color: "green",
              },
            }}
          >
            <InsightsIcon />
          </IconButton>
          <IconButton
            aria-label="assistant"
            sx={{
              "&:hover": {
                color: "purple",
              },
            }}
          >
            <AssistantIcon />
          </IconButton>
          <IconButton
            aria-label="delete"
            onClick={handleDelete}
            sx={{
              "&:hover": {
                color: "black",
              },
            }}
          >
            <DeleteIcon />
          </IconButton>
          <IconButton
            aria-label="view"
            onClick={openPDFViewer}
            sx={{
              "&:hover": {
                color: "blue",
              },
            }}
          >
            <RemoveRedEyeIcon />
          </IconButton>
        </CardActions>
      </Card>
      <EditDocumentDialog
        open={editDialogOpen}
        onClose={() => setEditDialogOpen(false)}
        document={document}
        onDocumentUpdate={onDocumentChange}
      />
    </>
  );
}
