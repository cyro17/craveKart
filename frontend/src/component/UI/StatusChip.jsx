import { Chip } from "@mui/material";

export const ACTIVE_STATUSES = [
    "CREATED",
    "PENDING",
    "CONFIRMED",
    "PREPARING",
    "READY_FOR_PICKUP",
    "OUT_FOR_DELIVERY",
];

export const FAILED_STATUSES = ["CANCELLED", "FAILED"];

export default function StatusChip({ status }) {
    let color = "default";

    if (ACTIVE_STATUSES.includes(status)) {
        color = "success"; // 🟢 green
    } else if (FAILED_STATUSES.includes(status)) {
        color = "error"; // 🔴 red
    }

    return (
        <Chip
            label={status.replaceAll("_", " ")}
            color={color}
            variant="filled"
            size="small"
            sx={{ fontWeight: 500 }}
        />
    );
}
