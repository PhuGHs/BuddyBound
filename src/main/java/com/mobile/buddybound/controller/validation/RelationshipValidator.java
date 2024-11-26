package com.mobile.buddybound.controller.validation;

import com.mobile.buddybound.exception.InvalidRelationshipException;
import com.mobile.buddybound.model.dto.RelationshipDto;
import com.mobile.buddybound.model.enumeration.FamilyRole;
import com.mobile.buddybound.model.enumeration.FamilyType;
import com.mobile.buddybound.model.enumeration.RelationshipType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class RelationshipValidator {
    private static final Map<FamilyType, FamilyRoleValidator> familyValidators = new EnumMap<>(FamilyType.class);

    static {
        familyValidators.put(FamilyType.PARENT_CHILD, new ParentChildValidator());
        familyValidators.put(FamilyType.SIBLING, new SiblingValidator());
        familyValidators.put(FamilyType.SPOUSE, new SpouseValidator());
    }

    public static void validateFamilyRelationship(RelationshipDto dto) {
        if (!RelationshipType.FAMILY.equals(dto.getRelationshipType())) {
            return;
        }

        FamilyRoleValidator validator = familyValidators.get(dto.getFamilyType());
        if (validator != null) {
            validator.validate(dto.getSenderRole(), dto.getReceiverRole());
        }
    }
}

interface FamilyRoleValidator {
    void validate(FamilyRole senderRole, FamilyRole receiverRole);
}

class ParentChildValidator implements FamilyRoleValidator {
    private static final Set<FamilyRole> VALID_PARENT_ROLES = Set.of(FamilyRole.FATHER, FamilyRole.MOTHER);

    @Override
    public void validate(FamilyRole senderRole, FamilyRole receiverRole) {
        if (!VALID_PARENT_ROLES.contains(senderRole)) {
            throw new InvalidRelationshipException("The sender must be a father or mother");
        }

        if (!FamilyRole.CHILD.equals(receiverRole)) {
            throw new InvalidRelationshipException("The receiver must be a child");
        }
    }
}

class SiblingValidator implements FamilyRoleValidator {
    @Override
    public void validate(FamilyRole senderRole, FamilyRole receiverRole) {
        if (!FamilyRole.SIBLING.equals(senderRole) || !FamilyRole.SIBLING.equals(receiverRole)) {
            throw new InvalidRelationshipException("Both sender and receiver role must be sibling");
        }
    }
}

class SpouseValidator implements FamilyRoleValidator {
    @Override
    public void validate(FamilyRole senderRole, FamilyRole receiverRole) {
        if (!FamilyRole.SPOUSE.equals(senderRole) || !FamilyRole.SPOUSE.equals(receiverRole)) {
            throw new InvalidRelationshipException("Both sender and receiver role must be spouse");
        }
    }
}