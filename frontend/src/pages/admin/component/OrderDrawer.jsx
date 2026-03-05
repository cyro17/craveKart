import React from "react";
import StatusBadge from "./StatusBadge";
import { X } from "lucide-react";

export default function OrderDrawer({ order: o, onClose }) {
    if (!o) return null;

    const subtotal = o.items.reduce((a, i) => a + i.price * i.qty, 0);
    const delivery = o.amount - subtotal > 0 ? o.amount - subtotal : 30;

    return (
        <>
            <div onClick={onclose} />
            <div>
                {/* Header */}
                <div>
                    <div>
                        <div>{o.id}</div>
                        <div>
                            {o.date} . {o.time}
                        </div>
                    </div>
                    <div>
                        <StatusBadge status={o.status} />
                        <button>
                            <X size={16} />
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
}
